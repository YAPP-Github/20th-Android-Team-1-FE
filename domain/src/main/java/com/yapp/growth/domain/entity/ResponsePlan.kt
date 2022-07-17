package com.yapp.growth.domain.entity

data class ResponsePlan(
    val users: List<User>,
    val colors: List<Int>,
    val totalCount: Int,
    val timeTableDate: List<TimeTableDate>,
    val id: Long,
    val promisingName: String,
    val owner: User,
    val minTime: String,
    val maxTime: String,
    val availableDate: List<String>,
    val hourList: List<String>
)
