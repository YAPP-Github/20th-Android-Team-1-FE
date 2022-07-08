package com.yapp.growth.domain.entity

import java.util.*

data class RespondPlan(
    val date: String,
    val hours: List<String>,
    val timeList: MutableList<Boolean>
)
