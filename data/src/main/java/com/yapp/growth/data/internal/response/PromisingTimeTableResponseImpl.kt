package com.yapp.growth.data.internal.response

import com.yapp.growth.data.response.*

data class PromisingTimeTableResponseImpl(
    override val users: List<UserResponseImpl>,
    override val colors: List<Int>,
    override val totalCount: Int,
    override val unit: Int,
    override val timeTable: List<TimeTableDateImpl>,
    override val id: Long,
    override val promisingName: String,
    override val owner: UserResponseImpl,
    override val minTime: String,
    override val maxTime: String,
    override val category: CategoryResponseImpl,
    override val availableDates: List<String>,
    override val placeName: String
): PromisingTimeTableResponse

data class UserResponseImpl (
    override val id: Int,
    override val userName: String
): UserResponse

data class TimeTableDateImpl (
    override val date: String,
    override val blocks: List<TimeTableUnit>
): TimeTableDate

data class CategoryResponseImpl (
    override val id: Long,
    override val keyword: String
): CategoryResponse
