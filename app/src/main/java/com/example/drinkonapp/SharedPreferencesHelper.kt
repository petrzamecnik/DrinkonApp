package com.example.drinkonapp

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("SearchHistoryPrefs", Context.MODE_PRIVATE)

    fun saveSearchQuery(query: String) {
        val existingHistory = preferences.getString("searchHistory", "")
        val newHistory = if (existingHistory.isNullOrEmpty()) {
            query
        } else {
            "$existingHistory, $query"
        }
        preferences.edit().putString("searchHistory", newHistory).apply()
    }

    fun getSearchHistory(): String {
        return preferences.getString("searchHistory", "") ?: ""
    }

    fun getReversedSearchHistory(): List<String> {
        val history = getSearchHistory()
        return if (history.isNotEmpty()) history.split(",").reversed() else emptyList()
    }

    fun clearSearchHistory() {
        preferences.edit().remove("searchHistory").apply()
    }
}
