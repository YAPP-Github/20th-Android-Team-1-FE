package com.yapp.growth.data.response

interface WaitingPlanResponse {
    val id: Long
    val title: String
    val leader: UserResponse
    val isLeader: Boolean // TODO: API 수정 요청
    val startTime: String
    val endTime: String
    val category: CategoryResponse
    val availableDate: String
    val members: List<String>
    val place: String
}
