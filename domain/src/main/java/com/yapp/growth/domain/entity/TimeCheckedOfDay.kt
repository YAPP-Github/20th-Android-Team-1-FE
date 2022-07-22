package com.yapp.growth.domain.entity

data class TimeCheckedOfDay(
    val date: String,
    val timeList: MutableList<Boolean>
)
