package com.yapp.growth.data.response

interface PromisingTimeTableResponse {
    val members: List<UserResponse>
    val colors: List<Long>
    val totalCount: Int
    val unit: Float
    val timeTable: List<TimeTableDateResponse>
    val id: Long
    val promisingName: String
    val owner: UserResponse
    val minTime: String
    val maxTime: String
    val category: CategoryResponse
    val availableDates: List<String>
    val placeName: String
}

interface UserResponse {
    val id: Long
    val userName: String
}

interface TimeTableDateResponse {
    val date: String
    val blocks: List<TimeTableUnitResponse>
}

interface TimeTableUnitResponse {
    val index: Int
    val count: Int
    val color: Long
    val users: List<UserResponse>
}

interface CategoryResponse {
    val id: Long
    val keyword: String
}

