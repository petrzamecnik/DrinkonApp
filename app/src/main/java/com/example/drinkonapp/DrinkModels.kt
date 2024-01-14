package com.example.drinkonapp

import com.squareup.moshi.Json

data class DrinkResponse(
    @Json(name = "drinks") val drinks: List<Drink>
)

data class Drink(
    @Json(name = "idDrink") val id: String,
    @Json(name = "strDrink") val name: String,
    @Json(name = "strCategory") val category: String?,
    @Json(name = "strInstructions") val instructions: String?,
    @Json(name = "strDrinkThumb") val thumbnail: String?
)
