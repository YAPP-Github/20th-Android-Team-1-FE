package com.yapp.growth.domain.entity

data class SendingResponsePlan(
    val date: String,
    val hours: List<String>,
    val timeList: MutableList<Boolean>
)
