package com.example.data

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

data class RemoteUserRecord(
    val userId: String,
    val name: String,
    val phone: String,
    val email: String
)

class MongoDbService {
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    fun isConfigured(): Boolean {
        val baseUrl = BuildConfig.BACKEND_BASE_URL.trim()
        return baseUrl.isNotEmpty() && baseUrl.startsWith("http")
    }

    private fun baseUrl(): String = BuildConfig.BACKEND_BASE_URL.trim().trimEnd('/')

    private fun buildUrl(path: String): String = "${baseUrl()}$path"

    private fun parseErrorMessage(responseBody: String?): String {
        if (responseBody.isNullOrBlank()) return "Request failed."
        return try {
            JSONObject(responseBody).optString("error").ifBlank { responseBody }
        } catch (_: Exception) {
            responseBody
        }
    }

    private suspend fun requestJson(
        method: String,
        path: String,
        token: String? = null,
        body: JSONObject? = null
    ): Result<JSONObject> = withContext(Dispatchers.IO) {
        if (!isConfigured()) {
            return@withContext Result.failure(Exception("Backend is not configured."))
        }

        val requestBuilder = Request.Builder().url(buildUrl(path))
            .addHeader("Accept", "application/json")

        if (!token.isNullOrBlank()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        when (method) {
            "GET" -> requestBuilder.get()
            "POST", "PUT" -> {
                val payload = (body ?: JSONObject()).toString()
                requestBuilder.method(method, payload.toRequestBody("application/json; charset=utf-8".toMediaType()))
            }
            else -> return@withContext Result.failure(IllegalArgumentException("Unsupported method: $method"))
        }

        try {
            client.newCall(requestBuilder.build()).execute().use { response ->
                val responseBody = response.body?.string()
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception(parseErrorMessage(responseBody)))
                }

                val parsed = if (responseBody.isNullOrBlank()) JSONObject() else JSONObject(responseBody)
                Result.success(parsed)
            }
        } catch (e: Exception) {
            Log.e("MongoDbService", "HTTP request failed for $path", e)
            Result.failure(e)
        }
    }

    private fun encode(value: String): String = URLEncoder.encode(value, "UTF-8")

    private fun parseUser(userJson: JSONObject): RemoteUserRecord {
        return RemoteUserRecord(
            userId = userJson.optString("userId"),
            name = userJson.optString("name"),
            phone = userJson.optString("phone"),
            email = userJson.optString("email")
        )
    }

    private fun parseProfile(profileJson: JSONObject): UserProfile {
        return UserProfile(
            id = 1,
            name = profileJson.optString("name"),
            phone = profileJson.optString("phone"),
            email = profileJson.optString("email"),
            avatarIndex = profileJson.optInt("avatarIndex", 0),
            dateOfBirth = profileJson.optString("dateOfBirth"),
            gender = profileJson.optString("gender"),
            schoolName = profileJson.optString("schoolName"),
            grade = profileJson.optString("grade"),
            state = profileJson.optString("state"),
            district = profileJson.optString("district"),
            preferredLanguage = profileJson.optString("preferredLanguage", "English"),
            selectedInterests = profileJson.optString("selectedInterests"),
            dream = profileJson.optString("dream"),
            onboardingCompleted = profileJson.optBoolean("onboardingCompleted", false),
            registered = profileJson.optBoolean("registered", true),
            totalXp = profileJson.optInt("totalXp", 0),
            currentStreak = profileJson.optInt("currentStreak", 0),
            enrolledBatchId = profileJson.optString("enrolledBatchId")
        )
    }

    private fun parseProgressList(progressJson: JSONArray): List<LessonProgress> {
        return buildList {
            for (index in 0 until progressJson.length()) {
                val item = progressJson.optJSONObject(index) ?: continue
                add(
                    LessonProgress(
                        lessonId = item.optString("lessonId"),
                        moduleId = item.optString("moduleId"),
                        completed = item.optBoolean("completed", false),
                        progressPercent = item.optInt("progressPercent", 0)
                    )
                )
            }
        }
    }

    suspend fun isPhoneRegistered(phone: String): Result<Boolean> {
        return requestJson("GET", "/auth/users/by-phone?phone=${encode(phone.trim())}")
            .map { response ->
                response.optJSONObject("data")
                    ?.optBoolean("exists", false)
                    ?: false
            }
    }

    suspend fun getUserByEmail(email: String): Result<RemoteUserRecord?> {
        return requestJson("GET", "/auth/users/by-email?email=${encode(email.trim().lowercase())}")
            .map { response ->
                val userJson = response.optJSONObject("data")?.optJSONObject("user")
                if (userJson == null) null else parseUser(userJson)
            }
    }

    suspend fun registerGoogleUser(name: String, email: String, phone: String, googleId: String): Result<String> {
        val body = JSONObject()
            .put("name", name)
            .put("email", email.trim().lowercase())
            .put("phone", phone.trim())
            .put("googleId", googleId)

        return requestJson("POST", "/auth/google-register", body = body)
            .map { response ->
                response.optJSONObject("data")?.optString("userId")
                    ?: throw IllegalStateException("Missing userId in backend response.")
            }
    }

    suspend fun signUp(name: String, phone: String, email: String, password: String): Result<String> {
        val body = JSONObject()
            .put("name", name)
            .put("phone", phone.trim())
            .put("email", email.trim().lowercase())
            .put("password", password)

        return requestJson("POST", "/auth/signup", body = body)
            .map { response ->
                response.optJSONObject("data")?.optString("userId")
                    ?: throw IllegalStateException("Missing userId in backend response.")
            }
    }

    suspend fun signIn(emailOrPhone: String, password: String): Result<Pair<String, String>> {
        val body = JSONObject()
            .put("emailOrPhone", emailOrPhone.trim())
            .put("password", password)

        return requestJson("POST", "/auth/login", body = body)
            .map { response ->
                val data = response.optJSONObject("data")
                    ?: throw IllegalStateException("Missing login payload from backend.")
                Pair(
                    data.optString("userId"),
                    data.optString("accessToken")
                )
            }
    }

    suspend fun saveProfile(userId: String, token: String, profile: UserProfile): Result<Unit> {
        val body = JSONObject()
            .put("name", profile.name)
            .put("phone", profile.phone)
            .put("email", profile.email.trim().lowercase())
            .put("avatarIndex", profile.avatarIndex)
            .put("dateOfBirth", profile.dateOfBirth)
            .put("gender", profile.gender)
            .put("schoolName", profile.schoolName)
            .put("grade", profile.grade)
            .put("state", profile.state)
            .put("district", profile.district)
            .put("preferredLanguage", profile.preferredLanguage)
            .put("selectedInterests", profile.selectedInterests)
            .put("dream", profile.dream)
            .put("onboardingCompleted", profile.onboardingCompleted)
            .put("registered", profile.registered)
            .put("totalXp", profile.totalXp)
            .put("currentStreak", profile.currentStreak)
            .put("enrolledBatchId", profile.enrolledBatchId)

        return requestJson("PUT", "/profiles/${encode(userId)}", token = token, body = body)
            .map { Unit }
    }

    suspend fun getProfile(userId: String, token: String): Result<UserProfile?> {
        return requestJson("GET", "/profiles/${encode(userId)}", token = token)
            .map { response ->
                val profileJson = response.optJSONObject("data")?.optJSONObject("profile")
                if (profileJson == null) null else parseProfile(profileJson)
            }
    }

    suspend fun saveLessonProgress(userId: String, token: String, progress: LessonProgress): Result<Unit> {
        val body = JSONObject()
            .put("moduleId", progress.moduleId)
            .put("completed", progress.completed)
            .put("progressPercent", progress.progressPercent)

        return requestJson(
            "PUT",
            "/progress/${encode(userId)}/${encode(progress.lessonId)}",
            token = token,
            body = body
        ).map { Unit }
    }

    suspend fun getAllLessonProgress(userId: String, token: String): Result<List<LessonProgress>> {
        return requestJson("GET", "/progress/${encode(userId)}", token = token)
            .map { response ->
                val progressJson = response.optJSONObject("data")?.optJSONArray("progress") ?: JSONArray()
                parseProgressList(progressJson)
            }
    }
}
