package com.yapp.growth.domain.entity

import java.util.*

data class RespondUsers(
    val users: List<Users>,
    val colors: List<String>,
    val minTime: Date,
    val maxTime: Date,
    val timeTable: List<TimeTable>,
    val avaliableDate: List<Date>
)
