package com.yapp.growth.domain.entity

data class TimeTableUnit(
    val index: Int,
    val count: Int,
    val users: List<User>,
    val color: Long
)
