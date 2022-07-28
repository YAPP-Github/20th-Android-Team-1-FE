package com.yapp.growth.data.parameter

import com.squareup.moshi.Json

data class TemporaryPlanParameter(
    @Json(name = "promisingName")
    val promisingName: String,
    @Json(name = "minTime")
    val minTime: String,
    @Json(name = "maxTime")
    val maxTime: String,
    @Json(name = "categoryId")
    val categoryId: Int,
    @Json(name = "availableDates")
    val availableDates: List<String>,
    @Json(name = "placeName")
    val placeName: String,
)
