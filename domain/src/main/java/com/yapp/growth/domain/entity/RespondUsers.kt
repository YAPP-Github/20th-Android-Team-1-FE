package com.yapp.growth.domain.entity

data class RespondUsers(
    val avaliableDate: List<String>,
    val users: List<User>,
    val colors: List<Int>,
    val totalCount: Int,
    val minTime: String,
    val maxTime: String,
    val timeTable: List<TimeTable>,
    val hourList: List<String>
)