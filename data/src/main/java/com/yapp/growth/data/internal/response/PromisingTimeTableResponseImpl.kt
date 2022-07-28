package com.yapp.growth.data.internal.response

import com.yapp.growth.data.response.*

data class PromisingTimeTableResponseImpl(
    override val members: List<UserResponseImpl>,
    override val colors: List<Long>,
    override val totalCount: Int,
    override val unit: Float,
    override val timeTable: List<TimeTableDateResponseImpl>,
    override val id: Long,
    override val promisingName: String,
    override val owner: UserResponseImpl,
    override val minTime: String,
    override val maxTime: String,
    override val category: CategoryResponseImpl,
    override val availableDates: List<String>,
    override val placeName: String
): PromisingTimeTableResponse

data class TimeTableDateResponseImpl (
    override val date: String,
    override val blocks: List<TimeTableUnitResponseImpl>
): TimeTableDateResponse

data class TimeTableUnitResponseImpl (
    override val index: Int,
    override val count: Int,
    override val color: Long,
    override val users: List<UserResponseImpl>
): TimeTableUnitResponse
