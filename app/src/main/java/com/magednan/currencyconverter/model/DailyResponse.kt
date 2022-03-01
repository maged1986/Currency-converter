package com.magednan.currencyconverter.model

import com.google.gson.JsonObject

data class DailyResponse(
    val base: String,
    val date: String,
    val historical: Boolean,
    val rates: Map<String, Double>?,
    val success: Boolean,
    val timestamp: Int
)
