package com.yapp.growth.domain.entity

import java.util.Date

data class TimeTable(
    val date: Date,
    val count: Int,
    val users: List<Users>,
    val color: String
)
