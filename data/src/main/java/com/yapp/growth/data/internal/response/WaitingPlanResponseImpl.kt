package com.yapp.growth.data.internal.response

import com.squareup.moshi.Json
import com.yapp.growth.data.response.WaitingPlanResponse

data class WaitingPlanResponseImpl(
    @Json(name = "id")
    override val id: Int,
    @Json(name = "promisingName")
    override val title: String,
    @Json(name = "isOwner")
    override val isLeader: Boolean,
    @Json(name = "minTime")
    override val startTime: String,
    @Json(name = "maxTime")
    override val endTime: String,
    @Json(name = "category")
    override val category: CategoryResponseImpl,
    @Json(name = "availableDates")
    override val availableDates: List<String>,
    @Json(name = "members")
    override val members: List<UserResponseImpl>,
    @Json(name = "placeName")
    override val place: String,
) : WaitingPlanResponse
