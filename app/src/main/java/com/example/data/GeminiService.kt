package com.example.data

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class GeminiService {
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    private fun String.escapeJson(): String {
        return this.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
    }

    suspend fun getGeminiResponse(prompt: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey == "MY_GEMINI_API_KEY" || apiKey.isEmpty()) {
            return@withContext "नमस्ते! मैं 'दिशारा मित्र' हूँ।\n\nआप मुझसे कोडिंग या डिज़ाइन के बारे में कुछ भी पूछ सकते हैं!\n\n*(Note: This is a preview. To enable active server-side AI answers, configure a real API key in the Secrets panel.)*"
        }

        // Using standard Google Generative AI REST Endpoint for Gemini 3.5 Flash
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

        val escapedPrompt = prompt.escapeJson()
        val jsonPayload = """
            {
              "contents": [{
                "parts": [{
                  "text": "$escapedPrompt"
                }]
              }],
              "systemInstruction": {
                "parts": [{
                  "text": "You are 'Dishara Mitra' (दिशारा मित्र), an encouraging and expert AI tutor for 'Dishara', a specialized IT learning app for Indian students. Explain concepts in a blend of Hindi and English (Hinglish), keeping explanations simple, friendly, easy to scan, and direct. Use relevant code snippets, formatting, and emojis where helpful."
                }]
              }
            }
        """.trimIndent()

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonPayload.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string() ?: ""
                if (!response.isSuccessful) {
                    Log.e("GeminiService", "API error: Code ${response.code}, Body: $responseBody")
                    return@withContext "माफ़ी चाहता हूँ, मुझे जवाब तैयार करने में कुछ दिक्कत हो रही है। (Error ${response.code})"
                }

                // Parse the response using manual JSON extraction to remain extremely resilient
                // Response matches schema: { "candidates": [ { "content": { "parts": [ { "text": "RESPONSE_TEXT" } ] } } ] }
                val textPattern = "\"text\":\\s*\"([^\"]+)\"".toRegex()
                val matches = textPattern.findAll(responseBody)
                val textResult = StringBuilder()
                for (match in matches) {
                    val partText = match.groupValues[1]
                    val cleaned = partText.replace("\\n", "\n")
                        .replace("\\\"", "\"")
                        .replace("\\t", "    ")
                        .replace("\\\\", "\\")
                    textResult.append(cleaned)
                }

                if (textResult.isNotEmpty()) {
                    textResult.toString()
                } else {
                    "नमस्ते! मैं 'दिशारा मित्र' हूँ। मुझे आपका प्रश्न समझ आया, कृपया दोबारा पूछें।"
                }
            }
        } catch (e: Exception) {
            Log.e("GeminiService", "Network call failed", e)
            "सर्वर से कनेक्ट करने में असमर्थ। कृपया अपना इंटरनेट कनेक्शन जांचें।"
        }
    }
}
