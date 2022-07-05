package com.yapp.growth.domain.entity

import java.util.*

data class Promising(
    val date: Date,
    val hours: List<String>,
    val timeList: MutableList<Boolean>
)
