package com.yapp.growth.data.response

interface WaitingPlanResponse {
    val id: Int
    val title: String
    val leader: UserResponse
    val isLeader: Boolean // TODO: API 수정 요청
    val startTime: String
    val endTime: String
    val category: CategoryResponse
    val availableDate: String
    val members: List<UserResponse>
    val place: String
}
