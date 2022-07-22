package com.yapp.growth.data.response

interface FixedPlanResponse {
    val id: Long
    val promiseName: String
    val promiseDate: String
    val owner: UserResponse
    val isOwner: Boolean
    val category: CategoryResponse
    val members: List<UserResponse>
    val placeName: String
}
