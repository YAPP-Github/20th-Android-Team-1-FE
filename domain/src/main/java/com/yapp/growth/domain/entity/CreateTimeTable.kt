package com.yapp.growth.domain.entity

data class CreateTimeTable(
    val totalCount: Int,
    val minTime: String,
    val maxTime: String,
    var availableDates: List<String>,
    val hourList: List<String>,
)
