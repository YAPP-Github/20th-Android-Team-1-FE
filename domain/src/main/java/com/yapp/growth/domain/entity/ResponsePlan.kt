package com.yapp.growth.domain.entity

data class ResponsePlan(
    val users: List<User>,
    val colors: List<Long>,
    val totalCount: Int,
    val timeTableDate: List<TimeTableDate>,
    val id: Long,
    val promisingName: String,
    val owner: User,
    val minTime: String,
    val maxTime: String,
    val availableDates: List<String>,
    val hourList: List<String>
)
