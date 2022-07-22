package com.yapp.growth.data.internal.response

import com.yapp.growth.data.response.CreateTimeTableResponse

data class CreateTimeTableResponseImpl(
    override val minTime: String,
    override val maxTime: String,
    override val totalCount: Int,
    override val unit: Float,
    override val availableDates: List<String>
): CreateTimeTableResponse
