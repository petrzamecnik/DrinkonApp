package com.example.drinkonapp

import okhttp3.OkHttpClient
import okhttp3.Request

object NetworkUtils {
    private val client = OkHttpClient()

    fun getJson(url: String): String? {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            return if (response.isSuccessful) {
                response.body?.string()
            } else {
                null
            }
        }
    }
}
