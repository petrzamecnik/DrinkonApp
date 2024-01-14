package com.example.drinkonapp

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object JsonParser {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun parseDrinksJson(jsonString: String): List<Drink>? {
        val adapter = moshi.adapter(DrinkResponse::class.java)
        return adapter.fromJson(jsonString)?.drinks
    }
}
