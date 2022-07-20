package com.yapp.growth.data.response

interface CreateTimeTableResponse {
    val minTime: String
    val maxTime: String
    val totalCount: Int
    val unit: Float
    val availableDates: List<String>
}
