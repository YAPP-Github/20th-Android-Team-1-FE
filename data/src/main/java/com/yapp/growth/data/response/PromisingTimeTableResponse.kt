package com.yapp.growth.data.response

interface PromisingTimeTableResponse {
    val users: List<UserResponse>
    val colors: List<Int>
    val totalCount: Int
    val unit: Int
    val timeTable: List<TimeTableDate>
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
    val id: Int
    val userName: String
}

interface TimeTableDate {
    val date: String
    val blocks: List<TimeTableUnit>
}

interface TimeTableUnit {
    val index: Int
    val count: Int
    val color: Int
    val users: List<UserResponse>
}

interface CategoryResponse {
    val id: Long
    val keyword: String
}

