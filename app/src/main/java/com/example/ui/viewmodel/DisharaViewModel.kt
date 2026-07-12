package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ui.theme.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.delay
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import java.util.concurrent.TimeUnit
import com.example.data.*

enum class AppScreen {
    ONBOARDING,
    AUTH,
    PROFILE_BASIC,
    PROFILE_ACADEMIC,
    PROFILE_INTERESTS,
    PROFILE,
    HOME,
    STUDY_PLAN,
    LEARN_COURSES,
    MODULE_DETAIL,
    LESSON_PLAYER,
    QUIZ,
    QUIZ_RESULTS,
    BATCH_DETAILS
}

// User Profile Data structure for UI state and database
data class UserState(
    val name: String = "",
    val avatarIndex: Int = 0,
    val dateOfBirth: String = "",
    val gender: String = "",
    val schoolName: String = "",
    val grade: String = "",
    val state: String = "",
    val district: String = "",
    val preferredLanguage: String = "English",
    val selectedInterests: List<String> = emptyList(),
    val dream: String = "",
    val totalXp: Int = 0,
    val currentStreak: Int = 0,
    val onboardingCompleted: Boolean = false,
    val registered: Boolean = false,
    val enrolledBatchId: String = "" // Empty string means not enrolled in any batch yet
)

data class TeacherNotification(
    val id: String,
    val title: String,
    val content: String,
    val timestamp: String,
    val senderName: String,
    val isImportant: Boolean = false,
    val isRead: Boolean = false
)

class DisharaViewModel(
    private val repository: com.example.data.DisharaRepository,
    private val geminiService: com.example.data.GeminiService,
    private val sharedPrefs: android.content.SharedPreferences
) : ViewModel() {

    // MongoDB Service Client
    private val mongoService = com.example.data.MongoDbService()

    private val _mongoUserId = MutableStateFlow(sharedPrefs.getString("mongo_uid", ""))
    val mongoUserId: StateFlow<String?> = _mongoUserId.asStateFlow()

    private val _mongoToken = MutableStateFlow(sharedPrefs.getString("mongo_token", ""))
    val mongoToken: StateFlow<String?> = _mongoToken.asStateFlow()

    val isMongoDbConfigured = mongoService.isConfigured()

    private fun saveMongoSession(userId: String, token: String) {
        sharedPrefs.edit()
            .putString("mongo_uid", userId)
            .putString("mongo_token", token)
            .apply()
        _mongoUserId.value = userId
        _mongoToken.value = token
    }

    private fun clearMongoSession() {
        sharedPrefs.edit()
            .remove("mongo_uid")
            .remove("mongo_token")
            .apply()
        _mongoUserId.value = ""
        _mongoToken.value = ""
    }

    private fun applyRemoteProfile(profile: com.example.data.UserProfile) {
        _tempProfile.value = UserState(
            name = profile.name,
            avatarIndex = profile.avatarIndex,
            dateOfBirth = profile.dateOfBirth,
            gender = profile.gender,
            schoolName = profile.schoolName,
            grade = profile.grade,
            state = profile.state,
            district = profile.district,
            preferredLanguage = profile.preferredLanguage,
            selectedInterests = profile.selectedInterests.split(",").filter { it.isNotEmpty() },
            dream = profile.dream,
            totalXp = profile.totalXp,
            currentStreak = profile.currentStreak,
            onboardingCompleted = profile.onboardingCompleted,
            registered = profile.registered,
            enrolledBatchId = profile.enrolledBatchId
        )
    }

    // Firebase Auth instances with graceful fallback if google-services.json is missing
    private var firebaseAuth: FirebaseAuth? = null
    
    // Firebase Phone Verification States
    private var verificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    private val _isAuthLoading = MutableStateFlow(false)
    val isAuthLoading: StateFlow<Boolean> = _isAuthLoading.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    init {
        try {
            firebaseAuth = FirebaseAuth.getInstance()
        } catch (e: Exception) {
            // Firebase is not configured, running in local database fallback mode
        }
    }

    fun signUpWithFirebase(fullName: String, phone: String, email: String, pass: String, onSuccess: () -> Unit) {
        _isAuthLoading.value = true
        _authError.value = null

        val phoneTrimmed = phone.trim()
        val nameTrimmed = fullName.trim()
        val passTrimmed = pass.trim()

        if (nameTrimmed.isEmpty() || phoneTrimmed.isEmpty() || passTrimmed.isEmpty()) {
            _authError.value = "Please fill in all required fields (Name, Phone, Password)"
            _isAuthLoading.value = false
            return
        }

        viewModelScope.launch {
            if (mongoService.isConfigured()) {
                val result = mongoService.signUp(nameTrimmed, phoneTrimmed, email, passTrimmed)
                _isAuthLoading.value = false
                result.onSuccess { userId ->
                    saveMongoSession(userId, userId)
                    updateTempName(nameTrimmed)
                    val entity = com.example.data.UserProfile(
                        id = 1,
                        name = nameTrimmed,
                        phone = phoneTrimmed,
                        email = email.trim().lowercase(),
                        onboardingCompleted = false,
                        registered = true
                    )
                    repository.saveProfile(entity)
                    mongoService.saveProfile(userId, userId, entity)
                    applyRemoteProfile(entity)
                    onSuccess()
                }.onFailure { error ->
                    _isAuthLoading.value = false
                    val isConnectionError = error.message?.let { msg ->
                        msg.contains("timeout", ignoreCase = true) ||
                        msg.contains("socket", ignoreCase = true) ||
                        msg.contains("connect", ignoreCase = true) ||
                        msg.contains("ssl", ignoreCase = true) ||
                        msg.contains("handshake", ignoreCase = true) ||
                        msg.contains("unreachable", ignoreCase = true) ||
                        msg.contains("host", ignoreCase = true) ||
                        msg.contains("not configured", ignoreCase = true) ||
                        msg.contains("initialize", ignoreCase = true)
                    } ?: true

                    if (isConnectionError) {
                        _authError.value = "Cannot connect to MongoDB.\n\nThis is usually caused by your IP address being blocked by MongoDB Atlas. Please configure 'Network Access' in your MongoDB Atlas dashboard and add IP address '0.0.0.0/0' (Allow access from anywhere) to connect securely.\n\nDetails: ${error.localizedMessage}"
                    } else {
                        _authError.value = error.localizedMessage ?: "Registration failed"
                    }
                }
            } else {
                _isAuthLoading.value = false
                _authError.value = "MongoDB backend is not configured."
            }
        }
    }

    // Google Sign In with Phone verification
    private val _showGooglePhoneRegisterDialog = MutableStateFlow(false)
    val showGooglePhoneRegisterDialog: StateFlow<Boolean> = _showGooglePhoneRegisterDialog.asStateFlow()

    private var googlePendingUserInfo: Triple<String, String, String>? = null // (Name, Email, GoogleID)

    fun cancelGooglePhoneRegistration() {
        _showGooglePhoneRegisterDialog.value = false
        googlePendingUserInfo = null
        _isAuthLoading.value = false
    }

    fun completeGoogleRegistration(phone: String, onSuccess: () -> Unit) {
        val info = googlePendingUserInfo
        if (info == null) {
            _authError.value = "Registration information is missing."
            return
        }
        val (name, email, googleId) = info
        val phoneTrimmed = phone.trim()
        if (phoneTrimmed.isEmpty()) {
            _authError.value = "Please enter your phone number."
            return
        }

        _isAuthLoading.value = true
        _authError.value = null

        viewModelScope.launch {
            val result = mongoService.registerGoogleUser(name, email, phoneTrimmed, googleId)
            _isAuthLoading.value = false
            result.onSuccess { userId ->
                saveMongoSession(userId, userId)
                
                // Save to Room and MongoDB profile
                val entity = com.example.data.UserProfile(
                    id = 1,
                    name = name,
                    phone = phoneTrimmed,
                    email = email.trim().lowercase(),
                    onboardingCompleted = false,
                    registered = true
                )
                repository.saveProfile(entity)
                if (mongoService.isConfigured()) {
                    mongoService.saveProfile(userId, userId, entity)
                }
                applyRemoteProfile(entity)
                
                _showGooglePhoneRegisterDialog.value = false
                googlePendingUserInfo = null
                onSuccess()
            }.onFailure { error ->
                _isAuthLoading.value = false
                val isConnectionError = error.message?.let { msg ->
                    msg.contains("timeout", ignoreCase = true) ||
                    msg.contains("socket", ignoreCase = true) ||
                    msg.contains("connect", ignoreCase = true) ||
                    msg.contains("ssl", ignoreCase = true) ||
                    msg.contains("handshake", ignoreCase = true) ||
                    msg.contains("unreachable", ignoreCase = true) ||
                    msg.contains("host", ignoreCase = true) ||
                    msg.contains("not configured", ignoreCase = true) ||
                    msg.contains("initialize", ignoreCase = true)
                } ?: true

                if (isConnectionError) {
                    _authError.value = "Cannot connect to MongoDB.\n\nThis is usually caused by your IP address being blocked by MongoDB Atlas. Please configure 'Network Access' in your MongoDB Atlas dashboard and add IP address '0.0.0.0/0' (Allow access from anywhere) to connect securely.\n\nDetails: ${error.localizedMessage}"
                } else {
                    _authError.value = error.localizedMessage ?: "Google registration failed."
                }
            }
        }
    }

    fun signInWithGoogleToken(idToken: String, onSuccess: () -> Unit) {
        _isAuthLoading.value = true
        _authError.value = null

        val auth = firebaseAuth ?: FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    val email = firebaseUser?.email ?: ""
                    val name = firebaseUser?.displayName ?: email.substringBefore("@")
                    val uid = firebaseUser?.uid ?: ""

                    checkGoogleUserAndLogin(name, email, uid, onSuccess)
                } else {
                    _isAuthLoading.value = false
                    _authError.value = task.exception?.localizedMessage ?: "Google Sign-In failed"
                }
            }
    }

    fun initializeGoogleFallbackSession(name: String, email: String) {
        _isAuthLoading.value = true
        val sanitizedEmail = email.trim().lowercase().replace("@", "_").replace(".", "_")
        val fallbackUid = "google_$sanitizedEmail"
        checkGoogleUserAndLogin(name, email, fallbackUid) {
            navigateTo(AppScreen.PROFILE_BASIC)
        }
    }

    private fun checkGoogleUserAndLogin(name: String, email: String, googleId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (mongoService.isConfigured()) {
                val checkResult = mongoService.getUserByEmail(email)
                _isAuthLoading.value = false
                checkResult.onSuccess { userRecord ->
                    if (userRecord != null && userRecord.phone.trim().isNotEmpty()) {
                        // User has phone number registered, complete login!
                        val userId = userRecord.userId
                        saveMongoSession(userId, userId)
                        
                        // Fetch profile
                        val profileResult = mongoService.getProfile(userId, userId)
                        profileResult.onSuccess { remoteProfile ->
                            if (remoteProfile != null) {
                                repository.saveProfile(remoteProfile)
                                applyRemoteProfile(remoteProfile)
                            }
                        }
                        onSuccess()
                    } else {
                        // Needs phone number registration!
                        googlePendingUserInfo = Triple(name, email, googleId)
                        _showGooglePhoneRegisterDialog.value = true
                    }
                }.onFailure {
                    // Try to registering as new google user
                    googlePendingUserInfo = Triple(name, email, googleId)
                    _showGooglePhoneRegisterDialog.value = true
                }
            } else {
                _isAuthLoading.value = false
                _authError.value = "MongoDB backend is not configured."
            }
        }
    }

    fun loginWithFirebase(emailOrPhone: String, pass: String, onSuccess: () -> Unit) {
        _isAuthLoading.value = true
        _authError.value = null

        val input = emailOrPhone.trim()
        val passTrimmed = pass.trim()
        if (input.isEmpty() || passTrimmed.isEmpty()) {
            _authError.value = "Please enter both credentials"
            _isAuthLoading.value = false
            return
        }

        viewModelScope.launch {
            if (mongoService.isConfigured()) {
                val result = mongoService.signIn(input, passTrimmed)
                _isAuthLoading.value = false
                result.onSuccess { (userId, accessToken) ->
                    saveMongoSession(userId, accessToken)
                    
                    // Fetch user profile from MongoDB
                    val profileResult = mongoService.getProfile(userId, accessToken)
                    profileResult.onSuccess { remoteProfile ->
                        if (remoteProfile != null) {
                            repository.saveProfile(remoteProfile)
                            applyRemoteProfile(remoteProfile)
                        }
                    }

                    // Fetch lesson progress from MongoDB and sync with Room
                    val progressResult = mongoService.getAllLessonProgress(userId, accessToken)
                    progressResult.onSuccess { remoteProgressList ->
                        remoteProgressList.forEach { progress ->
                            repository.saveProgress(progress)
                        }
                    }

                    onSuccess()
                }.onFailure { error ->
                    _isAuthLoading.value = false
                    val isConnectionError = error.message?.let { msg ->
                        msg.contains("timeout", ignoreCase = true) ||
                        msg.contains("socket", ignoreCase = true) ||
                        msg.contains("connect", ignoreCase = true) ||
                        msg.contains("ssl", ignoreCase = true) ||
                        msg.contains("handshake", ignoreCase = true) ||
                        msg.contains("unreachable", ignoreCase = true) ||
                        msg.contains("host", ignoreCase = true) ||
                        msg.contains("not configured", ignoreCase = true) ||
                        msg.contains("initialize", ignoreCase = true)
                    } ?: true

                    if (isConnectionError) {
                        _authError.value = "Cannot connect to MongoDB.\n\nThis is usually caused by your IP address being blocked by MongoDB Atlas. Please configure 'Network Access' in your MongoDB Atlas dashboard and add IP address '0.0.0.0/0' (Allow access from anywhere) to connect securely.\n\nDetails: ${error.localizedMessage}"
                    } else {
                        _authError.value = error.localizedMessage ?: "Login failed"
                    }
                }
            } else {
                _isAuthLoading.value = false
                _authError.value = "MongoDB backend is not configured."
            }
        }
    }

    fun clearAuthError() {
        _authError.value = null
    }

    fun startDemoOtpFlow() {
        _isAuthLoading.value = false
        _authError.value = "Demo OTP Mode enabled! Enter code '123456' to verify."
        verificationId = "demo_verification_id"
        _otpSent.value = true
    }

    fun loginWithDemoAccount(onSuccess: () -> Unit) {
        _isAuthLoading.value = true
        _authError.value = null
        viewModelScope.launch {
            delay(500)
            _isAuthLoading.value = false
            val demoUid = "demo_user_logged_in"
            updateTempName("Demo Student")
            saveMongoSession(demoUid, demoUid)
            
            val entity = com.example.data.UserProfile(
                id = 1,
                name = "Demo Student",
                onboardingCompleted = true,
                registered = true
            )
            repository.saveProfile(entity)
            if (mongoService.isConfigured()) {
                mongoService.saveProfile(demoUid, demoUid, entity)
            }
            onSuccess()
        }
    }

    // Global navigation & screen management state
    private val _currentScreen = MutableStateFlow(AppScreen.ONBOARDING)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Screen navigation stack for back actions
    private val screenStack = mutableListOf<AppScreen>()

    fun navigateTo(screen: AppScreen) {
        screenStack.add(_currentScreen.value)
        _currentScreen.value = screen
    }

    fun navigateBack() {
        if (screenStack.isNotEmpty()) {
            _currentScreen.value = screenStack.removeAt(screenStack.size - 1)
        } else {
            // Default fallbacks
            _currentScreen.value = AppScreen.HOME
        }
    }

    // Onboarding Carousel State
    private val _onboardingSlide = MutableStateFlow(0)
    val onboardingSlide: StateFlow<Int> = _onboardingSlide.asStateFlow()

    fun nextOnboardingSlide() {
        if (_onboardingSlide.value < 3) {
            _onboardingSlide.value += 1
        } else {
            navigateTo(AppScreen.AUTH)
        }
    }

    fun setOnboardingSlide(slide: Int) {
        _onboardingSlide.value = slide
    }

    // Auth screen toggles
    private val _isSignUpTab = MutableStateFlow(true)
    val isSignUpTab: StateFlow<Boolean> = _isSignUpTab.asStateFlow()

    private val _otpSent = MutableStateFlow(false)
    val otpSent: StateFlow<Boolean> = _otpSent.asStateFlow()

    private val _otpValues = MutableStateFlow(List(6) { "" })
    val otpValues: StateFlow<List<String>> = _otpValues.asStateFlow()

    fun toggleAuthTab(signUp: Boolean) {
        _isSignUpTab.value = signUp
        _otpSent.value = false
    }

    fun sendOtp() {
        _otpSent.value = true
    }

    fun setOtpValue(index: Int, value: String) {
        val updated = _otpValues.value.toMutableList()
        if (index in 0..5) {
            updated[index] = value
            _otpValues.value = updated
        }
    }

    // Temporary basic profile setup variables
    private val _tempProfile = MutableStateFlow(UserState())
    val tempProfile: StateFlow<UserState> = _tempProfile.asStateFlow()

    fun updateTempName(name: String) {
        _tempProfile.value = _tempProfile.value.copy(name = name)
    }

    fun selectAvatar(index: Int) {
        _tempProfile.value = _tempProfile.value.copy(avatarIndex = index)
    }

    fun updateTempDoB(dob: String) {
        _tempProfile.value = _tempProfile.value.copy(dateOfBirth = dob)
    }

    fun updateTempGender(gender: String) {
        _tempProfile.value = _tempProfile.value.copy(gender = gender)
    }

    fun updateTempSchool(schoolName: String) {
        _tempProfile.value = _tempProfile.value.copy(schoolName = schoolName)
    }

    fun updateTempGrade(grade: String) {
        val autoBatch = when (grade) {
            "Grade 9" -> "udaan"
            "Grade 10" -> "batch_10"
            "Grade 11" -> "pragati"
            "Grade 12" -> "batch_12"
            "College & Career" -> "lakshya"
            else -> ""
        }
        _tempProfile.value = _tempProfile.value.copy(
            grade = grade,
            enrolledBatchId = autoBatch
        )
    }

    fun updateTempState(state: String) {
        _tempProfile.value = _tempProfile.value.copy(state = state)
    }

    fun updateTempDistrict(district: String) {
        _tempProfile.value = _tempProfile.value.copy(district = district)
    }

    fun updateTempLanguage(language: String) {
        _tempProfile.value = _tempProfile.value.copy(preferredLanguage = language)
    }

    fun toggleInterest(interest: String) {
        val list = _tempProfile.value.selectedInterests.toMutableList()
        if (list.contains(interest)) {
            list.remove(interest)
        } else if (list.size < 3) {
            list.add(interest)
        }
        _tempProfile.value = _tempProfile.value.copy(selectedInterests = list)
    }

    fun updateTempDream(dream: String) {
        _tempProfile.value = _tempProfile.value.copy(dream = dream)
    }

    // Plan Generation State
    private val _isGeneratingPlan = MutableStateFlow(false)
    val isGeneratingPlan: StateFlow<Boolean> = _isGeneratingPlan.asStateFlow()

    private val _planProgress = MutableStateFlow(0f)
    val planProgress: StateFlow<Float> = _planProgress.asStateFlow()

    private val _funFactIndex = MutableStateFlow(0)
    val funFactIndex: StateFlow<Int> = _funFactIndex.asStateFlow()

    val funFacts = listOf(
        "क्या आप जानते हैं? कोडिंग की पहली भाषा एक महिला, एडा लवलेस ने लिखी थी!",
        "दुनिया का पहला कंप्यूटर 'ENIAC' एक पूरे कमरे जितना बड़ा था।",
        "सॉफ्टवेयर में 'Bug' का नाम एक असली कीड़े (Moth) की वजह से पड़ा था!",
        "इंटरनेट पर हर सेकंड करीब 40,000 गूगल सर्च होते हैं।",
        "दुनिया की 90% जानकारी पिछले 2 सालों में ही बनी है!"
    )

    fun startPlanGeneration() {
        viewModelScope.launch {
            _isGeneratingPlan.value = true
            _planProgress.value = 0f
            _funFactIndex.value = 0

            // Simulate progress count and change fun facts
            for (i in 1..100) {
                delay(30)
                _planProgress.value = i / 100f
                if (i == 30 || i == 60 || i == 90) {
                    _funFactIndex.value = (_funFactIndex.value + 1) % funFacts.size
                }
            }

            // Save user profile state inside Room database securely
            val p = _tempProfile.value.copy(
                onboardingCompleted = true,
                registered = true
            )
            saveUserProfile(p)

            _isGeneratingPlan.value = false
            _currentScreen.value = AppScreen.HOME
        }
    }

    // Database Sync and Live State Flow
    val persistedProfile = repository.userProfile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val dbProgressList = repository.allProgress.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        // Initial setup from persisted profile if exists
        viewModelScope.launch {
            repository.userProfile.collect { profile ->
                if (profile != null && profile.onboardingCompleted) {
                    _tempProfile.value = UserState(
                        name = profile.name,
                        avatarIndex = profile.avatarIndex,
                        dateOfBirth = profile.dateOfBirth,
                        gender = profile.gender,
                        schoolName = profile.schoolName,
                        grade = profile.grade,
                        state = profile.state,
                        district = profile.district,
                        preferredLanguage = profile.preferredLanguage,
                        selectedInterests = profile.selectedInterests.split(",").filter { it.isNotEmpty() },
                        dream = profile.dream,
                        totalXp = profile.totalXp,
                        currentStreak = profile.currentStreak,
                        onboardingCompleted = profile.onboardingCompleted,
                        registered = profile.registered,
                        enrolledBatchId = profile.enrolledBatchId
                    )
                    // Auto nav to HOME if already configured
                    _currentScreen.value = AppScreen.HOME
                }
            }
        }
    }

    private fun saveUserProfile(user: UserState) {
        viewModelScope.launch {
            val currentProfile = persistedProfile.value
            val entity = com.example.data.UserProfile(
                id = 1,
                name = user.name,
                phone = currentProfile?.phone.orEmpty(),
                email = currentProfile?.email.orEmpty(),
                avatarIndex = user.avatarIndex,
                dateOfBirth = user.dateOfBirth,
                gender = user.gender,
                schoolName = user.schoolName,
                grade = user.grade,
                state = user.state,
                district = user.district,
                preferredLanguage = user.preferredLanguage,
                selectedInterests = user.selectedInterests.joinToString(","),
                dream = user.dream,
                onboardingCompleted = user.onboardingCompleted,
                registered = user.registered,
                totalXp = user.totalXp,
                currentStreak = user.currentStreak,
                enrolledBatchId = user.enrolledBatchId
            )
            repository.saveProfile(entity)

            // Sync to MongoDB if configured & logged in
            val userId = _mongoUserId.value ?: ""
            val token = _mongoToken.value ?: ""
            if (mongoService.isConfigured() && userId.isNotEmpty() && token.isNotEmpty()) {
                mongoService.saveProfile(userId, token, entity)
            }
        }
    }

    fun enrollInBatch(batchId: String) {
        val updated = _tempProfile.value.copy(enrolledBatchId = batchId)
        _tempProfile.value = updated
        saveUserProfile(updated)
    }

    fun saveProfileUpdate(user: UserState) {
        val autoBatch = when (user.grade) {
            "Grade 9" -> "udaan"
            "Grade 10" -> "batch_10"
            "Grade 11" -> "pragati"
            "Grade 12" -> "batch_12"
            "College & Career" -> "lakshya"
            else -> user.enrolledBatchId
        }
        val updated = user.copy(enrolledBatchId = autoBatch)
        _tempProfile.value = updated
        saveUserProfile(updated)
    }

    private val _isDrawerOpen = MutableStateFlow(false)
    val isDrawerOpen: StateFlow<Boolean> = _isDrawerOpen.asStateFlow()

    fun setDrawerOpen(open: Boolean) {
        _isDrawerOpen.value = open
    }

    fun toggleLanguage() {
        val updated = _tempProfile.value.copy(preferredLanguage = "English")
        _tempProfile.value = updated
        saveUserProfile(updated)
    }

    fun logout() {
        viewModelScope.launch {
            repository.saveProfile(com.example.data.UserProfile(
                id = 1,
                name = "",
                avatarIndex = 0,
                dateOfBirth = "",
                gender = "",
                schoolName = "",
                grade = "",
                state = "",
                district = "",
                preferredLanguage = "English",
                selectedInterests = "",
                dream = "",
                onboardingCompleted = false,
                registered = false,
                totalXp = 0,
                currentStreak = 0,
                enrolledBatchId = ""
            ))
            
            clearMongoSession()
            
            _tempProfile.value = UserState(
                name = "",
                avatarIndex = 0,
                dateOfBirth = "",
                gender = "",
                schoolName = "",
                grade = "",
                state = "",
                district = "",
                preferredLanguage = "English",
                selectedInterests = emptyList(),
                dream = "",
                onboardingCompleted = false,
                registered = false,
                totalXp = 0,
                currentStreak = 0,
                enrolledBatchId = ""
            )
            _currentScreen.value = AppScreen.ONBOARDING
        }
    }

    // Lesson Complete Toggle
    fun markLessonComplete(lessonId: String, moduleId: String, completed: Boolean) {
        viewModelScope.launch {
            val progress = com.example.data.LessonProgress(
                lessonId = lessonId,
                moduleId = moduleId,
                completed = completed,
                progressPercent = if (completed) 100 else 0
            )
            repository.saveProgress(progress)

            // Grant +5 XP upon completing a lecture!
            var updatedProfile: com.example.data.UserProfile? = null
            if (completed) {
                persistedProfile.value?.let { currentProfile ->
                    val updated = currentProfile.copy(totalXp = currentProfile.totalXp + 5)
                    repository.saveProfile(updated)
                    updatedProfile = updated
                }
                // Automatically launch the interactive lesson practice quiz!
                startLessonMiniQuiz(lessonId)
            }

            // Sync to MongoDB if configured & logged in
            val userId = _mongoUserId.value ?: ""
            val token = _mongoToken.value ?: ""
            if (mongoService.isConfigured() && userId.isNotEmpty() && token.isNotEmpty()) {
                mongoService.saveLessonProgress(userId, token, progress)
                updatedProfile?.let {
                    mongoService.saveProfile(userId, token, it)
                }
            }
        }
    }

    // Quiz and score rewards states
    private val _quizScore = MutableStateFlow(80)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    // --- ADVANCED QUIZ SYSTEM ---
    
    private val _passedWorlds = MutableStateFlow<Set<Int>>(emptySet())
    val passedWorlds: StateFlow<Set<Int>> = _passedWorlds.asStateFlow()
    
    // Quiz Result Info Class
    data class QuizResultInfo(
        val worldIndex: Int,
        val lessonId: String?,
        val isWorldQuiz: Boolean,
        val correctCount: Int,
        val totalCount: Int,
        val passed: Boolean,
        val attemptNumber: Int,
        val questions: List<QuizQuestion>,
        val userAnswers: Map<Int, Int>
    )

    private val _currentQuizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val currentQuizQuestions: StateFlow<List<QuizQuestion>> = _currentQuizQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val selectedAnswers: StateFlow<Map<Int, Int>> = _selectedAnswers.asStateFlow()

    private val _isWorldQuiz = MutableStateFlow(true)
    val isWorldQuiz: StateFlow<Boolean> = _isWorldQuiz.asStateFlow()

    private val _quizWorldIndex = MutableStateFlow(1)
    val quizWorldIndex: StateFlow<Int> = _quizWorldIndex.asStateFlow()

    private val _quizLessonId = MutableStateFlow<String?>(null)
    val quizLessonId: StateFlow<String?> = _quizLessonId.asStateFlow()

    private val _quizAttempts = MutableStateFlow<Map<String, Int>>(emptyMap())
    val quizAttempts: StateFlow<Map<String, Int>> = _quizAttempts.asStateFlow()

    private val _quizStudyMode = MutableStateFlow(false)
    val quizStudyMode: StateFlow<Boolean> = _quizStudyMode.asStateFlow()

    private val _completedStudySessions = MutableStateFlow<Set<String>>(emptySet())
    val completedStudySessions: StateFlow<Set<String>> = _completedStudySessions.asStateFlow()

    private val _lastQuizResult = MutableStateFlow<QuizResultInfo?>(null)
    val lastQuizResult: StateFlow<QuizResultInfo?> = _lastQuizResult.asStateFlow()

    // Real-time passed quizzes tracking
    private val _passedQuizzes = MutableStateFlow<Set<String>>(emptySet())
    val passedQuizzes: StateFlow<Set<String>> = _passedQuizzes.asStateFlow()

    // Fully functional teacher notification system
    private val _showNotificationsDialog = MutableStateFlow(false)
    val showNotificationsDialog: StateFlow<Boolean> = _showNotificationsDialog.asStateFlow()

    private val _notifications = MutableStateFlow<List<TeacherNotification>>(
        listOf(
            TeacherNotification(
                id = "notif_1",
                title = "Live Doubts Session Today",
                content = "Teacher Amit Kumar has scheduled a live doubt-clearing session at 4:00 PM today. Please be ready with your HTML/CSS queries!",
                timestamp = "Today, 10:30 AM",
                senderName = "Teacher Amit",
                isImportant = true,
                isRead = false
            ),
            TeacherNotification(
                id = "notif_2",
                title = "Udaan Class 9: Homework Alert",
                content = "Please make sure to complete the World 1 Grand Quiz to unlock your Badge and progress to the next module.",
                timestamp = "Yesterday, 3:15 PM",
                senderName = "Teacher Priya",
                isImportant = false,
                isRead = false
            ),
            TeacherNotification(
                id = "notif_3",
                title = "Quiz Update: Retake Helper",
                content = "Good news! If you attempt any quiz 3 or more times, you will now see correct answers to study from. We want everyone to succeed!",
                timestamp = "2 days ago",
                senderName = "System",
                isImportant = true,
                isRead = true
            )
        )
    )
    val notifications: StateFlow<List<TeacherNotification>> = _notifications.asStateFlow()

    fun setShowNotificationsDialog(show: Boolean) {
        _showNotificationsDialog.value = show
    }

    fun markNotificationAsRead(id: String) {
        _notifications.value = _notifications.value.map {
            if (it.id == id) it.copy(isRead = true) else it
        }
    }

    fun sendTeacherNotification(title: String, content: String, isImportant: Boolean) {
        val newNotif = TeacherNotification(
            id = "notif_${System.currentTimeMillis()}",
            title = title,
            content = content,
            timestamp = "Just now",
            senderName = "Teacher (Live)",
            isImportant = isImportant,
            isRead = false
        )
        _notifications.value = listOf(newNotif) + _notifications.value
    }

    fun selectQuizAnswer(questionIndex: Int, optionIndex: Int) {
        val updated = _selectedAnswers.value.toMutableMap()
        updated[questionIndex] = optionIndex
        _selectedAnswers.value = updated
    }

    fun nextQuizQuestion() {
        if (_currentQuestionIndex.value < _currentQuizQuestions.value.size - 1) {
            _currentQuestionIndex.value = _currentQuestionIndex.value + 1
        }
    }

    fun previousQuizQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value = _currentQuestionIndex.value - 1
        }
    }

    fun startWorldQuiz(worldIndex: Int) {
        val quizKey = "world_$worldIndex"
        val attempts = (_quizAttempts.value[quizKey] ?: 0) + 1
        
        val newAttemptsMap = _quizAttempts.value.toMutableMap()
        newAttemptsMap[quizKey] = attempts
        _quizAttempts.value = newAttemptsMap
        
        _isWorldQuiz.value = true
        _quizWorldIndex.value = worldIndex
        _quizLessonId.value = null
        _currentQuestionIndex.value = 0
        _selectedAnswers.value = emptyMap()
        
        val allQuestions = QuizProvider.getQuestionsForWorld(worldIndex)
        
        val filteredQuestions = when (attempts) {
            1 -> allQuestions
            2 -> {
                // "20% easier questions" -> Filter out "Hard" questions, limit to 20
                allQuestions.filter { it.difficulty != "Hard" }.take(20).ifEmpty { allQuestions.take(20) }
            }
            else -> {
                // Attempt 3: Similar pool
                allQuestions
            }
        }
        
        _currentQuizQuestions.value = filteredQuestions
        
        if (attempts >= 3 && !_completedStudySessions.value.contains(quizKey)) {
            _quizStudyMode.value = true
        } else {
            _quizStudyMode.value = false
        }
        
        navigateTo(AppScreen.QUIZ)
    }

    fun startLessonMiniQuiz(lessonId: String) {
        val quizKey = "lesson_$lessonId"
        val attempts = (_quizAttempts.value[quizKey] ?: 0) + 1
        
        val newAttemptsMap = _quizAttempts.value.toMutableMap()
        newAttemptsMap[quizKey] = attempts
        _quizAttempts.value = newAttemptsMap
        
        _isWorldQuiz.value = false
        _quizLessonId.value = lessonId
        _quizWorldIndex.value = 1 // Default
        _currentQuestionIndex.value = 0
        _selectedAnswers.value = emptyMap()
        
        val allQuestions = QuizProvider.getMiniQuizForLesson(lessonId)
        
        val filteredQuestions = when (attempts) {
            1 -> allQuestions
            2 -> {
                // 20% easier questions for 5-question pool: take 4 easiest
                allQuestions.sortedBy { if (it.difficulty == "Easy") 0 else if (it.difficulty == "Medium") 1 else 2 }.take(4)
            }
            else -> allQuestions
        }
        
        _currentQuizQuestions.value = filteredQuestions
        
        if (attempts >= 3 && !_completedStudySessions.value.contains(quizKey)) {
            _quizStudyMode.value = true
        } else {
            _quizStudyMode.value = false
        }
        
        navigateTo(AppScreen.QUIZ)
    }

    fun completeStudySession() {
        val quizKey = if (_isWorldQuiz.value) "world_${_quizWorldIndex.value}" else "lesson_${_quizLessonId.value}"
        _completedStudySessions.value = _completedStudySessions.value + quizKey
        _quizStudyMode.value = false
    }

    fun submitQuiz() {
        val questions = _currentQuizQuestions.value
        val answers = _selectedAnswers.value
        
        var correctCount = 0
        questions.forEachIndexed { index, question ->
            if (answers[index] == question.correctIndex) {
                correctCount++
            }
        }
        
        val totalCount = questions.size
        val scorePercent = if (totalCount > 0) (correctCount * 100) / totalCount else 0
        val passed = scorePercent >= 60
        
        val quizKey = if (_isWorldQuiz.value) "world_${_quizWorldIndex.value}" else "lesson_${_quizLessonId.value}"
        val attempt = _quizAttempts.value[quizKey] ?: 1
        
        val result = QuizResultInfo(
            worldIndex = _quizWorldIndex.value,
            lessonId = _quizLessonId.value,
            isWorldQuiz = _isWorldQuiz.value,
            correctCount = correctCount,
            totalCount = totalCount,
            passed = passed,
            attemptNumber = attempt,
            questions = questions,
            userAnswers = answers
        )
        _lastQuizResult.value = result
        _quizScore.value = scorePercent
        
        // Grant 1 XP per question completed (5 questions = 5 XP) on completing the quiz task
        viewModelScope.launch {
            val xpGrant = totalCount
            persistedProfile.value?.let { currentProfile ->
                val updated = currentProfile.copy(totalXp = currentProfile.totalXp + xpGrant)
                repository.saveProfile(updated)
                
                val userId = _mongoUserId.value ?: ""
                val token = _mongoToken.value ?: ""
                if (mongoService.isConfigured() && userId.isNotEmpty() && token.isNotEmpty()) {
                    mongoService.saveProfile(userId, token, updated)
                }
            }
        }

        if (passed) {
            _completedStudySessions.value = _completedStudySessions.value - quizKey
            if (_isWorldQuiz.value) {
                _passedWorlds.value = _passedWorlds.value + _quizWorldIndex.value
            } else {
                _quizLessonId.value?.let { lessonId ->
                    _passedQuizzes.value = _passedQuizzes.value + lessonId
                }
            }
        }
        
        navigateTo(AppScreen.QUIZ_RESULTS)
    }

    fun resetQuizAttempts() {
        val quizKey = if (_isWorldQuiz.value) "world_${_quizWorldIndex.value}" else "lesson_${_quizLessonId.value}"
        val updated = _quizAttempts.value.toMutableMap()
        updated[quizKey] = 0
        _quizAttempts.value = updated
        _completedStudySessions.value = _completedStudySessions.value - quizKey
        
        if (_isWorldQuiz.value) {
            startWorldQuiz(_quizWorldIndex.value)
        } else {
            _quizLessonId.value?.let { startLessonMiniQuiz(it) }
        }
    }

    fun completeQuiz(score: Int) {
        _quizScore.value = score
        viewModelScope.launch {
            // Unlock reward, add 50 XP
            persistedProfile.value?.let { currentProfile ->
                val updated = currentProfile.copy(totalXp = currentProfile.totalXp + 50)
                repository.saveProfile(updated)

                // Sync to MongoDB if configured & logged in
                val userId = _mongoUserId.value ?: ""
                val token = _mongoToken.value ?: ""
                if (mongoService.isConfigured() && userId.isNotEmpty() && token.isNotEmpty()) {
                    mongoService.saveProfile(userId, token, updated)
                }
            }
            navigateTo(AppScreen.QUIZ_RESULTS)
        }
    }

    // Dashboard dynamic items
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchBarVisible = MutableStateFlow(false)
    val searchBarVisible: StateFlow<Boolean> = _searchBarVisible.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleSearchBar() {
        _searchBarVisible.value = !_searchBarVisible.value
    }

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    fun setCourseCategory(category: String) {
        _selectedCategory.value = category
    }

    private val _activeWeek = MutableStateFlow(1)
    val activeWeek: StateFlow<Int> = _activeWeek.asStateFlow()

    fun setStudyPlanWeek(week: Int) {
        _activeWeek.value = week
    }

    private val _selectedModuleId = MutableStateFlow<String?>(null)
    val selectedModuleId: StateFlow<String?> = _selectedModuleId.asStateFlow()

    private val _selectedLessonId = MutableStateFlow<String?>(null)
    val selectedLessonId: StateFlow<String?> = _selectedLessonId.asStateFlow()

    fun selectModule(moduleId: String) {
        _selectedModuleId.value = moduleId
        navigateTo(AppScreen.MODULE_DETAIL)
    }

    fun selectLesson(lessonId: String) {
        _selectedLessonId.value = lessonId
        navigateTo(AppScreen.LESSON_PLAYER)
    }

    // Mitra AI Conversations list
    private val _mitraMessages = MutableStateFlow<List<Pair<String, Boolean>>>(
        listOf(
            "नमस्ते! मैं 'दिशारा मित्र' हूँ। आज आप क्या सीखना चाहते हैं?" to false
        )
    )
    val mitraMessages: StateFlow<List<Pair<String, Boolean>>> = _mitraMessages.asStateFlow()

    private val _mitraInput = MutableStateFlow("")
    val mitraInput: StateFlow<String> = _mitraInput.asStateFlow()

    private val _isMitraLoading = MutableStateFlow(false)
    val isMitraLoading: StateFlow<Boolean> = _isMitraLoading.asStateFlow()

    fun updateMitraInput(text: String) {
        _mitraInput.value = text
    }

    fun sendMitraMessage() {
        val userPrompt = _mitraInput.value.trim()
        if (userPrompt.isEmpty()) return

        val updatedList = _mitraMessages.value.toMutableList()
        updatedList.add(userPrompt to true)
        _mitraMessages.value = updatedList
        _mitraInput.value = ""
        _isMitraLoading.value = true

        viewModelScope.launch {
            try {
                val response = geminiService.getGeminiResponse(userPrompt)
                val newList = _mitraMessages.value.toMutableList()
                newList.add(response to false)
                _mitraMessages.value = newList
            } catch (e: Exception) {
                val newList = _mitraMessages.value.toMutableList()
                newList.add("माफ़ी चाहता हूँ, कुछ नेटवर्क समस्या आ गई है। कृपया पुनः प्रयास करें।" to false)
                _mitraMessages.value = newList
            } finally {
                _isMitraLoading.value = false
            }
        }
    }
}
