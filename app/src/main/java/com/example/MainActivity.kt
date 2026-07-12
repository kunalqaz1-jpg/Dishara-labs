package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.DisharaDatabase
import com.example.data.DisharaRepository
import com.example.data.GeminiService
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.DisharaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize state variables
        val database = DisharaDatabase.getInstance(applicationContext)
        val repository = DisharaRepository(database.disharaDao())
        val geminiService = GeminiService()
        val sharedPrefs = applicationContext.getSharedPreferences("dishara_prefs", android.content.Context.MODE_PRIVATE)
        val viewModel = DisharaViewModel(repository, geminiService, sharedPrefs)

        setContent {
            MyApplicationTheme {
                AppNavigator(viewModel = viewModel)
            }
        }
    }
}

// Data Classes for Sidebar Overlays
data class CommunityPost(
    val id: Int,
    val author: String,
    val grade: String,
    val title: String,
    val question: String,
    val upvotes: Int,
    val userUpvoted: Boolean,
    val answers: List<Pair<String, String>>
)

data class DownloadableLesson(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val fileType: String,
    val size: String,
    val progress: Float, // 0 to 1, -1 means not downloaded
    val isDownloaded: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigator(viewModel: DisharaViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val isDrawerOpen by viewModel.isDrawerOpen.collectAsState()
    val tempState by viewModel.tempProfile.collectAsState()
    val isEnglish = true
    val context = LocalContext.current

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Sync drawer states
    LaunchedEffect(isDrawerOpen) {
        if (isDrawerOpen) {
            drawerState.open()
        } else {
            drawerState.close()
        }
    }

    LaunchedEffect(drawerState.isOpen) {
        if (drawerState.isOpen != isDrawerOpen) {
            viewModel.setDrawerOpen(drawerState.isOpen)
        }
    }

    // Lock drawer gesture for login/onboarding
    val gesturesEnabled = when (currentScreen) {
        AppScreen.ONBOARDING,
        AppScreen.AUTH,
        AppScreen.PROFILE_BASIC,
        AppScreen.PROFILE_ACADEMIC,
        AppScreen.PROFILE_INTERESTS -> false
        else -> true
    }

    // Modal Overlays State
    var showMitraSheet by remember { mutableStateOf(false) }
    var showCommunitySheet by remember { mutableStateOf(false) }
    var showDownloadsSheet by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }
    var showLogoutConfirm by remember { mutableStateOf(false) }

    // Simulated downloads state
    var downloadsList by remember {
        mutableStateOf(
            listOf(
                DownloadableLesson("dl_1", "Intro to Scratch Blocks", "स्क्रैच ब्लॉक्स परिचय", "VIDEO", "15.4 MB", -1f, false),
                DownloadableLesson("dl_2", "HTML Tags & Document Structure", "HTML टैग्स और संरचना", "VIDEO", "22.1 MB", -1f, false),
                DownloadableLesson("dl_3", "Learn CSS Flexbox Cheat-Sheet", "CSS फ्लेक्सबॉक्स चीट-शीट", "PDF", "1.2 MB", -1f, false),
                DownloadableLesson("dl_4", "Variables and Scope in JavaScript", "JS वेरिएबल्स और स्कोप", "VIDEO", "28.5 MB", -1f, false)
            )
        )
    }

    // Live Forum state
    var communityPosts by remember {
        mutableStateOf(
            listOf(
                CommunityPost(
                    id = 1,
                    author = "अमित कुमार / Amit Kumar",
                    grade = "Grade 9",
                    title = "रिपीट ब्लॉक का क्या उपयोग है?",
                    question = "नमस्ते दोस्तों, स्क्रैच में 'Repeat' और 'Forever' ब्लॉक में क्या मुख्य अंतर होता है?",
                    upvotes = 14,
                    userUpvoted = false,
                    answers = listOf(
                        "निधि शर्मा / Nidhi Sharma" to "रिपीट ब्लॉक आपके कोड को केवल एक निश्चित संख्या (जैसे 10 बार) तक ही दोहराता है, जबकि फॉरएवर ब्लॉक इसे बिना रुके तब तक चलाता है जब तक आप लाल बटन न दबाएं।",
                        "Dishara Support" to "बिल्कुल सही! रिपीट का उपयोग निश्चित लूप के लिए किया जाता है।"
                    )
                ),
                CommunityPost(
                    id = 2,
                    author = "प्रिया सेन / Priya Sen",
                    grade = "Grade 10",
                    title = "CSS Margin vs Padding",
                    question = "What is the key visual difference between padding and margin? I keep mixing them up in layouts.",
                    upvotes = 9,
                    userUpvoted = false,
                    answers = listOf(
                        "राजीव रंजन / Rajiv Ranjan" to "Padding is the spacing INSIDE the border of the element, whereas Margin is the spacing OUTSIDE the border of the element. Think of margin as personal space around you!"
                    )
                )
            )
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        drawerContent = {
            if (gesturesEnabled) {
                DisharaDrawerContent(
                    viewModel = viewModel,
                    onClose = { viewModel.setDrawerOpen(false) },
                    onNavigate = { screen ->
                        viewModel.setDrawerOpen(false)
                        viewModel.navigateTo(screen)
                    },
                    onOpenMitra = {
                        viewModel.setDrawerOpen(false)
                        showMitraSheet = true
                    },
                    onOpenCommunity = {
                        viewModel.setDrawerOpen(false)
                        showCommunitySheet = true
                    },
                    onOpenDownloads = {
                        viewModel.setDrawerOpen(false)
                        showDownloadsSheet = true
                    },
                    onOpenSettings = {
                        viewModel.setDrawerOpen(false)
                        showSettingsDialog = true
                    },
                    onOpenHelp = {
                        viewModel.setDrawerOpen(false)
                        showHelpDialog = true
                    },
                    onOpenAbout = {
                        viewModel.setDrawerOpen(false)
                        showAboutDialog = true
                    },
                    onOpenPrivacy = {
                        viewModel.setDrawerOpen(false)
                        showPrivacyDialog = true
                    },
                    onOpenTerms = {
                        viewModel.setDrawerOpen(false)
                        showTermsDialog = true
                    },
                    onLogout = {
                        viewModel.setDrawerOpen(false)
                        showLogoutConfirm = true
                    }
                )
            }
        }
    ) {
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "ScreenNavigation"
        ) { screen ->
            when (screen) {
                AppScreen.ONBOARDING -> OnboardingScreen(viewModel = viewModel)
                AppScreen.AUTH -> AuthScreen(viewModel = viewModel)
                AppScreen.PROFILE_BASIC -> ProfileSetupBasicScreen(viewModel = viewModel)
                AppScreen.PROFILE_ACADEMIC -> ProfileSetupAcademicScreen(viewModel = viewModel)
                AppScreen.PROFILE_INTERESTS -> ProfileSetupInterestsScreen(viewModel = viewModel)
                AppScreen.PROFILE -> ProfileScreen(
                    viewModel = viewModel,
                    onOpenDownloads = { showDownloadsSheet = true },
                    onOpenPrivacy = { showPrivacyDialog = true },
                    onOpenTerms = { showTermsDialog = true },
                    onOpenHelp = { showHelpDialog = true },
                    onOpenAbout = { showAboutDialog = true }
                )
                AppScreen.HOME -> HomeScreen(viewModel = viewModel)
                AppScreen.LEARN_COURSES -> LearnCoursesScreen(viewModel = viewModel)
                AppScreen.STUDY_PLAN -> StudyPlanScreen(viewModel = viewModel)
                AppScreen.MODULE_DETAIL -> ModuleDetailScreen(viewModel = viewModel)
                AppScreen.LESSON_PLAYER -> LessonPlayerScreen(viewModel = viewModel)
                AppScreen.QUIZ -> QuizPlayerScreen(viewModel = viewModel)
                AppScreen.QUIZ_RESULTS -> QuizResultsScreen(viewModel = viewModel)
                AppScreen.BATCH_DETAILS -> BatchDetailsScreen(viewModel = viewModel)
            }
        }
    }

    // ==========================================
    // OVERLAY: DISHARA MITRA AI CHAT SHEET
    // ==========================================
    if (showMitraSheet) {
        ModalBottomSheet(
            onDismissRequest = { showMitraSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Surface
        ) {
            val messages by viewModel.mitraMessages.collectAsState()
            val currentInput by viewModel.mitraInput.collectAsState()
            val isLoading by viewModel.isMitraLoading.collectAsState()
            val listState = rememberLazyListState()

            LaunchedEffect(messages.size) {
                if (messages.isNotEmpty()) {
                    listState.animateScrollToItem(messages.size - 1)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(SecondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.SmartToy, contentDescription = null, tint = OnSecondaryContainer)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isEnglish) "Dishara Mitra AI" else "दिशारा मित्र AI",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                            Text(
                                text = if (isEnglish) "Active • Ask me coding queries" else "सक्रिय • कोडिंग सवाल पूछें",
                                style = MaterialTheme.typography.labelSmall,
                                color = Outline
                            )
                        }
                    }
                    IconButton(onClick = { showMitraSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(12.dp))

                Box(modifier = Modifier.weight(1f)) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(messages) { msg ->
                            val isUser = msg.second
                            val bubbleBg = if (isUser) Primary else SurfaceContainerHigh
                            val bubbleTextColor = if (isUser) Color.White else OnSurface
                            val alignment = if (isUser) Alignment.End else Alignment.Start

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = alignment
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 12.dp,
                                                topEnd = 12.dp,
                                                bottomStart = if (isUser) 12.dp else 2.dp,
                                                bottomEnd = if (isUser) 2.dp else 12.dp
                                            )
                                        )
                                        .background(bubbleBg)
                                        .padding(horizontal = 14.dp, vertical = 10.dp)
                                        .widthIn(max = 260.dp)
                                ) {
                                    Text(
                                        text = msg.first,
                                        color = bubbleTextColor,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        if (isLoading) {
                            item {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = Primary)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (isEnglish) "Mitra is typing..." else "मित्र लिख रहा है...",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Outline
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = currentInput,
                        onValueChange = { viewModel.updateMitraInput(it) },
                        placeholder = { Text(if (isEnglish) "Ask something..." else "कुछ पूछें...") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { viewModel.sendMitraMessage() },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Primary)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                    }
                }
            }
        }
    }

    // ==========================================
    // OVERLAY: COMMUNITY HUB DISCUSSION BOARD
    // ==========================================
    if (showCommunitySheet) {
        var showNewPostDialog by remember { mutableStateOf(false) }
        var selectedPostForAnswers by remember { mutableStateOf<CommunityPost?>(null) }

        ModalBottomSheet(
            onDismissRequest = { showCommunitySheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isEnglish) "Dishara Community Hub" else "दिशारा छात्र समुदाय",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                        Text(
                            text = if (isEnglish) "Ask questions and share logic" else "सवाल पूछें और कोडिंग चर्चा करें",
                            style = MaterialTheme.typography.labelSmall,
                            color = Outline
                        )
                    }
                    IconButton(onClick = { showCommunitySheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { showNewPostDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Secondary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isEnglish) "Post a Question / Idea" else "नया सवाल पूछें / विचार साझा करें", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(communityPosts) { post ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                            border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.4f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                                .background(PrimaryContainer),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = post.author.first().toString(),
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = OnPrimaryContainer
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(post.author, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                    }
                                    Text(post.grade, style = MaterialTheme.typography.labelSmall, color = Outline)
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(post.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Primary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(post.question, style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                communityPosts = communityPosts.map { p ->
                                                    if (p.id == post.id) {
                                                        if (p.userUpvoted) p.copy(upvotes = p.upvotes - 1, userUpvoted = false)
                                                        else p.copy(upvotes = p.upvotes + 1, userUpvoted = true)
                                                    } else p
                                                }
                                            }
                                            .padding(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ThumbUp,
                                            contentDescription = "Upvote",
                                            tint = if (post.userUpvoted) Primary else Outline,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "${post.upvotes}",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = if (post.userUpvoted) Primary else Outline
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { selectedPostForAnswers = post }
                                            .padding(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.Comment, contentDescription = "Answer count", tint = Outline, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "${post.answers.size} " + if (isEnglish) "Answers" else "उत्तर",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Outline
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add Post Dialog
        if (showNewPostDialog) {
            var title by remember { mutableStateOf("") }
            var body by remember { mutableStateOf("") }
            AlertDialog(
                onDismissRequest = { showNewPostDialog = false },
                title = { Text(if (isEnglish) "Ask the Community" else "समुदाय से पूछें") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            placeholder = { Text(if (isEnglish) "Short Title..." else "संक्षिप्त शीर्षक...") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = body,
                            onValueChange = { body = it },
                            placeholder = { Text(if (isEnglish) "Describe your coding query in detail..." else "अपना प्रश्न विस्तार से यहाँ लिखें...") },
                            modifier = Modifier.fillMaxWidth().height(100.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (title.isNotBlank() && body.isNotBlank()) {
                                val newId = (communityPosts.maxOfOrNull { it.id } ?: 0) + 1
                                val studentName = if (tempState.name.isEmpty()) "Student" else tempState.name
                                val studentGrade = if (tempState.grade.isEmpty()) "Grade 9" else tempState.grade
                                val newPost = CommunityPost(
                                    id = newId,
                                    author = studentName,
                                    grade = studentGrade,
                                    title = title,
                                    question = body,
                                    upvotes = 1,
                                    userUpvoted = true,
                                    answers = emptyList()
                                )
                                communityPosts = listOf(newPost) + communityPosts
                                showNewPostDialog = false
                                Toast.makeText(context, "Question Posted Successfully!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text(if (isEnglish) "Submit" else "भेजें")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showNewPostDialog = false }) {
                        Text(if (isEnglish) "Cancel" else "रद्द करें")
                    }
                }
            )
        }

        // Sub-Sheet / Detail Answers dialog
        selectedPostForAnswers?.let { activePost ->
            var myAnswer by remember { mutableStateOf("") }
            AlertDialog(
                onDismissRequest = { selectedPostForAnswers = null },
                title = { Text(activePost.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(activePost.question, style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
                        HorizontalDivider()
                        Text(if (isEnglish) "Answers / Solutions:" else "उत्तर / समाधान:", fontWeight = FontWeight.Bold, color = Primary)

                        activePost.answers.forEach { ans ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                                border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.3f))
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(ans.first, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = Secondary)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(ans.second, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = myAnswer,
                            onValueChange = { myAnswer = it },
                            placeholder = { Text(if (isEnglish) "Type your solution/answer here..." else "अपना उत्तर यहाँ लिखें...") },
                            modifier = Modifier.fillMaxWidth().height(80.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (myAnswer.isNotBlank()) {
                                val studentName = if (tempState.name.isEmpty()) "Student" else tempState.name
                                communityPosts = communityPosts.map { p ->
                                    if (p.id == activePost.id) {
                                        p.copy(answers = p.answers + (studentName to myAnswer))
                                    } else p
                                }
                                myAnswer = ""
                                selectedPostForAnswers = null
                                Toast.makeText(context, "Answer Submitted!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text(if (isEnglish) "Submit Answer" else "उत्तर दें")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { selectedPostForAnswers = null }) {
                        Text(if (isEnglish) "Close" else "बंद करें")
                    }
                }
            )
        }
    }

    // ==========================================
    // OVERLAY: OFFLINE DOWNLOADS MANAGER
    // ==========================================
    if (showDownloadsSheet) {
        val downloadScope = rememberCoroutineScope()
        ModalBottomSheet(
            onDismissRequest = { showDownloadsSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isEnglish) "Offline Download Manager" else "ऑफ़लाइन डाउनलोड प्रबंधक",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                        Text(
                            text = if (isEnglish) "Study anywhere without Internet data" else "बिना इंटरनेट के कहीं भी पढ़ाई करें",
                            style = MaterialTheme.typography.labelSmall,
                            color = Outline
                        )
                    }
                    IconButton(onClick = { showDownloadsSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(downloadsList) { lesson ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (lesson.fileType == "VIDEO") Icons.Default.PlayCircle else Icons.Default.Description,
                                            contentDescription = null,
                                            tint = Secondary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = lesson.fileType,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Secondary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(lesson.size, style = MaterialTheme.typography.labelSmall, color = Outline)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = if (isEnglish) lesson.titleEn else lesson.titleHi,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )

                                    if (lesson.progress in 0f..0.99f) {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        LinearProgressIndicator(
                                            progress = { lesson.progress },
                                            modifier = Modifier.fillMaxWidth(0.8f).height(4.dp),
                                            color = Primary
                                        )
                                    }
                                }

                                if (lesson.isDownloaded) {
                                    Button(
                                        onClick = {
                                            Toast.makeText(
                                                context,
                                                if (isEnglish) "Playing offline: ${lesson.titleEn}" else "ऑफ़लाइन वीडियो चल रहा है: ${lesson.titleHi}",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                        shape = RoundedCornerShape(12.dp),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp)
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.White)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(if (isEnglish) "Play" else "देखें", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                } else if (lesson.progress >= 0f) {
                                    Text(
                                        text = "${(lesson.progress * 100).toInt()}%",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Primary
                                    )
                                } else {
                                    IconButton(
                                        onClick = {
                                            downloadScope.launch {
                                                val idx = downloadsList.indexOfFirst { it.id == lesson.id }
                                                if (idx == -1) return@launch
                                                var p = 0f
                                                while (p < 1f) {
                                                    delay(200)
                                                    p += 0.15f
                                                    downloadsList = downloadsList.toMutableList().apply {
                                                        this[idx] = this[idx].copy(progress = p.coerceAtMost(1f))
                                                    }
                                                }
                                                downloadsList = downloadsList.toMutableList().apply {
                                                    this[idx] = this[idx].copy(progress = 1f, isDownloaded = true)
                                                }
                                                Toast.makeText(context, "Download completed!", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    ) {
                                        Icon(Icons.Default.Download, contentDescription = "Download", tint = Primary)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // ==========================================
    // OVERLAY: SETTINGS & CUSTOMIZATION DIALOG
    // ==========================================
    if (showSettingsDialog) {
        var notificationsEnabled by remember { mutableStateOf(true) }
        var darkModeEnabled by remember { mutableStateOf(false) }
        var showResetConfirm by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = {
                Text(
                    text = if (isEnglish) "Settings & Toggles" else "सेटिंग्स और विकल्प",
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Notification toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(if (isEnglish) "Push Notifications" else "पुश नोटिफिकेशन", fontWeight = FontWeight.SemiBold)
                            Text(if (isEnglish) "Receive study plan reminders" else "स्टडी प्लान रिमाइंडर प्राप्त करें", style = MaterialTheme.typography.labelSmall, color = Outline)
                        }
                        Switch(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
                    }

                    // Dark mode toggle simulation
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(if (isEnglish) "High Contrast Interface" else "उच्च कंट्रास्ट मोड", fontWeight = FontWeight.SemiBold)
                            Text(if (isEnglish) "Adapt display text style" else "टेक्स्ट शैली अनुकूलित करें", style = MaterialTheme.typography.labelSmall, color = Outline)
                        }
                        Switch(checked = darkModeEnabled, onCheckedChange = { darkModeEnabled = it })
                    }

                    HorizontalDivider()

                    // Danger Zone: Reset DB
                    Button(
                        onClick = { showResetConfirm = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Error),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isEnglish) "Clear Database Progress" else "डेटाबेस प्रगति रीसेट करें", fontWeight = FontWeight.Bold)
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showSettingsDialog = false }) {
                    Text(if (isEnglish) "Save Changes" else "सुरक्षित करें")
                }
            }
        )

        if (showResetConfirm) {
            AlertDialog(
                onDismissRequest = { showResetConfirm = false },
                title = { Text(if (isEnglish) "Are you absolutely sure?" else "क्या आप निश्चित हैं?") },
                text = { Text(if (isEnglish) "This action will clear all your learning progress and reset study badges. This cannot be undone." else "यह कार्रवाई आपकी सभी सीखने की प्रगति को मिटा देगी। इसे पूर्ववत नहीं किया जा सकता।") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.logout() // clear and logout safely
                            showResetConfirm = false
                            showSettingsDialog = false
                            Toast.makeText(context, "Database cleared!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Error)
                    ) {
                        Text(if (isEnglish) "Yes, Reset" else "हाँ, रीसेट करें")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetConfirm = false }) {
                        Text(if (isEnglish) "Cancel" else "रद्द करें")
                    }
                }
            )
        }
    }

    // ==========================================
    // OVERLAY: HELP CENTER & FAQS
    // ==========================================
    if (showHelpDialog) {
        var feedbackText by remember { mutableStateOf("") }
        var supportTicketSubmitted by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
            title = { Text(if (isEnglish) "Help & Support" else "सहायता और अक्सर पूछे जाने वाले प्रश्न", fontWeight = FontWeight.Bold, color = Primary) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(if (isEnglish) "Frequently Asked Questions (FAQ):" else "अक्सर पूछे जाने वाले प्रश्न (FAQ):", fontWeight = FontWeight.Bold, color = Secondary)

                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                if (isEnglish) "Q: How do I earn XP?" else "प्रश्न: मैं XP कैसे कमाऊं?",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                if (isEnglish) "A: Mark any module lessons complete to earn 50 XP instantly!" else "उत्तर: किसी भी मॉड्यूल के पाठों को पूर्ण चिह्नित करने पर तुरंत 50 XP प्राप्त करें!",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnSurfaceVariant
                            )
                        }
                    }

                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                if (isEnglish) "Q: Can I access lectures offline?" else "प्रश्न: क्या मैं ऑफ़लाइन पढ़ सकता हूँ?",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                if (isEnglish) "A: Yes! Tap 'Offline Downloads' in the sidebar to download study notes/videos." else "उत्तर: हाँ! स्टडी नोट्स/वीडियो डाउनलोड करने के लिए साइडबार में 'ऑफ़लाइन डाउनलोड' पर टैप करें।",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnSurfaceVariant
                            )
                        }
                    }

                    HorizontalDivider()

                    if (supportTicketSubmitted) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (isEnglish) "✓ Support inquiry submitted! We will respond shortly." else "✓ सहायता अनुरोध दर्ज कर लिया गया है! हम जल्द ही उत्तर देंगे।",
                                color = Color(0xFF2E7D32),
                                modifier = Modifier.padding(10.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        Text(if (isEnglish) "Still have queries? Submit a ticket:" else "कोई और प्रश्न है? हमें लिखें:", fontWeight = FontWeight.Bold)
                        OutlinedTextField(
                            value = feedbackText,
                            onValueChange = { feedbackText = it },
                            placeholder = { Text(if (isEnglish) "Write your support query here..." else "अपना प्रश्न यहाँ लिखें...") },
                            modifier = Modifier.fillMaxWidth().height(80.dp)
                        )
                        Button(
                            onClick = {
                                if (feedbackText.isNotBlank()) {
                                    supportTicketSubmitted = true
                                    feedbackText = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Secondary)
                        ) {
                            Text(if (isEnglish) "Submit Support Ticket" else "सहायता अनुरोध भेजें")
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showHelpDialog = false }) {
                    Text(if (isEnglish) "Close" else "बंद करें")
                }
            }
        )
    }

    // ==========================================
    // OVERLAY: ABOUT DISHARA DIALOG
    // ==========================================
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            icon = { Text("🎓", fontSize = 40.sp) },
            title = { Text("About Dishara / दिशारा के बारे में", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Dishara App v1.0.0",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = Primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "दिशारा का उद्देश्य भारत के सरकारी स्कूल के छात्रों को उनकी स्थानीय भाषा में उच्च-गुणवत्ता वाली कंप्यूटर और कोडिंग शिक्षा प्रदान करना है ताकि वे तकनीक के क्षेत्र में उज्ज्वल भविष्य बना सकें।\n\nDishara aims to deliver world-class IT education in native Indian languages to enable government school students to pursue careers in technology.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = OnSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "© 2026 Dishara Technologies. All rights reserved.",
                        style = MaterialTheme.typography.labelSmall,
                        color = Outline
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showAboutDialog = false }) {
                    Text(if (isEnglish) "Awesome" else "बहुत बढ़िया")
                }
            }
        )
    }

    // ==========================================
    // OVERLAYS: PRIVACY POLICY & TERMS OF SERVICE
    // ==========================================
    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text(if (isEnglish) "Privacy Policy" else "गोपनीयता नीति", fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.height(260.dp).verticalScroll(rememberScrollState())) {
                    Text(
                        text = "Dishara respects your privacy. We store user profile data, including name, school name, grade, learning progress, and active batch selections, purely locally on your device via the Room database.\n\nAll AI interactions with Dishara Mitra are securely parsed in accordance with standard Gemini API terms. We do not sell or distribute user statistics to third parties.\n\nIf you use the Firebase Auth backup features, your email/phone records are safely encrypted in our secured database nodes.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showPrivacyDialog = false }) {
                    Text(if (isEnglish) "Close" else "बंद करें")
                }
            }
        )
    }

    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false },
            title = { Text(if (isEnglish) "Terms of Service" else "सेवा की शर्तें", fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.height(260.dp).verticalScroll(rememberScrollState())) {
                    Text(
                        text = "By registering and enrolling in Dishara training programs (Udaan or Pragati), you agree to abide by active student guidelines.\n\nAll software curriculums, video lectures, and support materials provided by Dishara Mitra are for individual learning purposes. Reverse engineering or commercially distributing course contents is strictly prohibited.\n\nDishara does not charge students any fees for accessing learning modules or AI chat channels.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showTermsDialog = false }) {
                    Text(if (isEnglish) "Accept" else "स्वीकार करें")
                }
            }
        )
    }

    // ==========================================
    // OVERLAY: LOGOUT CONFIRMATION DIALOG
    // ==========================================
    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            title = { Text(if (isEnglish) "Logout Confirm" else "लॉगआउट की पुष्टि") },
            text = { Text(if (isEnglish) "Do you want to log out of your profile?" else "क्या आप अपने प्रोफ़ाइल से लॉगआउट करना चाहते हैं?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.logout()
                        showLogoutConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Error)
                ) {
                    Text(if (isEnglish) "Logout" else "लॉगआउट", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) {
                    Text(if (isEnglish) "Cancel" else "रद्द करें")
                }
            }
        )
    }
}

// ==========================================
// DRAWER SHEET LAYOUT COMPOSABLE
// ==========================================
@Composable
fun DisharaDrawerContent(
    viewModel: DisharaViewModel,
    onClose: () -> Unit,
    onNavigate: (AppScreen) -> Unit,
    onOpenMitra: () -> Unit,
    onOpenCommunity: () -> Unit,
    onOpenDownloads: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenHelp: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenPrivacy: () -> Unit,
    onOpenTerms: () -> Unit,
    onLogout: () -> Unit
) {
    val tempState by viewModel.tempProfile.collectAsState()
    val isEnglish = true

    val activeBatch = AvailableBatches.find { it.id == tempState.enrolledBatchId }

    ModalDrawerSheet(
        modifier = Modifier
            .width(320.dp)
            .fillMaxHeight(),
        drawerContainerColor = SurfaceContainerLowest,
        drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(bottom = 16.dp)
        ) {
            // 1. Header (☰ Dishara ✕)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Dishara",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
                IconButton(onClick = onClose, modifier = Modifier.size(40.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Menu",
                        tint = Outline
                    )
                }
            }

            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))

            // 2. User Section (👤 My Profile)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(AppScreen.PROFILE) }
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isEnglish) "My Profile" else "मेरा प्रोफाइल",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val avatarUrl = AvatarUrls.getOrNull(tempState.avatarIndex) ?: AvatarUrls[0]
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .border(2.dp, SecondaryContainer, CircleShape)
                    ) {
                        AsyncImage(
                            model = avatarUrl,
                            contentDescription = "Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = tempState.name.ifEmpty { if (isEnglish) "Guest Student" else "अतिथि छात्र" },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (tempState.enrolledBatchId.isEmpty()) {
                                if (isEnglish) "No Active Batch" else "कोई सक्रिय बैच नहीं"
                            } else {
                                if (isEnglish) {
                                    "Grade ${tempState.grade} • ${activeBatch?.nameEn ?: ""}"
                                } else {
                                    "कक्षा ${tempState.grade} • ${activeBatch?.nameHi ?: ""}"
                                }
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Streak & XP Badges Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Streak badge
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFFFE0B2))
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("🔥", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isEnglish) "${tempState.currentStreak} days" else "${tempState.currentStreak} दिन",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100)
                        )
                    }

                    // XP Badge
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFE1BEE7))
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("⭐", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${tempState.totalXp} XP",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnPrimaryContainer
                        )
                    }
                }
            }

            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))

            // 3. Primary Items
            DrawerGroupTitle(title = if (isEnglish) "Learning Hub" else "अधिगम केंद्र")

            DrawerItem(
                icon = Icons.AutoMirrored.Filled.MenuBook,
                label = if (isEnglish) "My Study Plan" else "मेरा स्टडी प्लान",
                onClick = { onNavigate(AppScreen.STUDY_PLAN) }
            )
            DrawerItem(
                icon = Icons.Default.SmartToy,
                label = if (isEnglish) "Dishara Mitra" else "दिशारा मित्र AI",
                onClick = onOpenMitra
            )
            DrawerItem(
                icon = Icons.Default.Groups,
                label = if (isEnglish) "Community" else "चर्चा / समुदाय",
                onClick = onOpenCommunity
            )

            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))

            // 4. Secondary Items
            DrawerGroupTitle(title = if (isEnglish) "Offline" else "ऑफ़लाइन")
            DrawerItem(
                icon = Icons.Default.Download,
                label = if (isEnglish) "Offline Downloads" else "ऑफ़लाइन डाउनलोड",
                onClick = onOpenDownloads
            )

            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))

            // 5. Settings & Support
            DrawerGroupTitle(title = if (isEnglish) "Support & Help" else "सहायता और सेटिंग्स")
            DrawerItem(
                icon = Icons.Default.Settings,
                label = if (isEnglish) "Settings" else "सेटिंग्स",
                onClick = onOpenSettings
            )
            DrawerItem(
                icon = Icons.Default.Help,
                label = if (isEnglish) "Help & Support" else "सहायता और सहायता",
                onClick = onOpenHelp
            )
            DrawerItem(
                icon = Icons.Default.Info,
                label = if (isEnglish) "About Dishara" else "दिशारा के बारे में",
                onClick = onOpenAbout
            )

            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))

            // 7. Legal
            DrawerItem(
                icon = Icons.Default.Lock,
                label = if (isEnglish) "Privacy Policy" else "गोपनीयता नीति",
                onClick = onOpenPrivacy
            )
            DrawerItem(
                icon = Icons.Default.Description,
                label = if (isEnglish) "Terms of Service" else "सेवा की शर्तें",
                onClick = onOpenTerms
            )

            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))

            // 8. Logout
            DrawerItem(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                label = if (isEnglish) "Logout" else "लॉगआउट करें",
                iconTint = Error,
                textColor = Error,
                onClick = onLogout
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))

            // 9. Version
            Text(
                text = "v1.0.0",
                style = MaterialTheme.typography.labelMedium,
                color = Outline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun DrawerGroupTitle(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = Outline,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
    )
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    iconTint: Color = OnSurfaceVariant,
    textColor: Color = OnSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconTint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}
