package com.yapp.growth.domain.entity

data class TemporaryPlan(
    val title: String,
    val startHour: Int,
    val endHour: Int,
    val categoryId: Int,
    val availableDates: List<String>,
    val place: String,
)
