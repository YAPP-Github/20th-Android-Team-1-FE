package com.yapp.growth.data.response

interface WaitingPlanResponse {
    val id: Int
    val title: String
    val isLeader: Boolean
    val startTime: String
    val endTime: String
    val category: CategoryResponse
    val availableDates: List<String>
    val members: List<UserResponse>
    val place: String
}
