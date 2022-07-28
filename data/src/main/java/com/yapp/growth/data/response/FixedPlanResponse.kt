package com.yapp.growth.data.response

interface FixedPlanResponse {
    val id: Int
    val title: String
    val date: String
    val leader: UserResponse
    val isLeader: Boolean
    val category: CategoryResponse
    val members: List<UserResponse>
    val place: String
}
