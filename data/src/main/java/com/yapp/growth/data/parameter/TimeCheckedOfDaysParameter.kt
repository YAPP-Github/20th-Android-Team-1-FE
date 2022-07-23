package com.yapp.growth.data.parameter

data class TimeCheckedOfDaysParameter(
    val unit: Float,
    val timeTable: List<TimeCheckedOfDayParameter>
)

data class TimeCheckedOfDayParameter(
    val date: String,
    val times: List<Boolean>
)
