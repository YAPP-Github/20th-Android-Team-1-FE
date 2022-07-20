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

interface UserResponse {
    val id: Long
    val userName: String
}

interface CategoryResponse {
    val id: Long
    val keyword: String
}