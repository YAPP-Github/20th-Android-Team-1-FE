package com.yapp.growth.domain.entity

data class TimeTable(
    val users: List<User>,
    val colors: List<Long>,
    val totalCount: Int,
    val timeTableDate: List<TimeTableDate>,
    val id: Long,
    val promisingName: String,
    val owner: User,
    val minTime: String,
    val maxTime: String,
    val availableDates: List<String>,
    val hourList: List<String>,
    val placeName: String,
    val categoryName: String,
    val category: Category,
)

data class TimeTableDate(
    val date: String,
    val timeTableUnits: List<TimeTableUnit>
)

data class TimeTableUnit(
    val index: Int,
    val count: Int,
    val users: List<User>,
    val color: Long
)
