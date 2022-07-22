package com.yapp.growth.data.internal.response

import com.google.gson.annotations.SerializedName
import com.yapp.growth.data.response.CategoryResponse
import com.yapp.growth.data.response.UserResponse
import com.yapp.growth.data.response.WaitingPlanResponse

data class WaitingPlanResponseImpl(
    @SerializedName("id")
    override val id: Long,
    @SerializedName("promisingName")
    override val title: String,
    @SerializedName("owner")
    override val leader: UserResponseImpl,
    @SerializedName("minTime")
    override val startTime: String,
    @SerializedName("maxTime")
    override val endTime: String,
    @SerializedName("category")
    override val category: CategoryResponseImpl,
    @SerializedName("availableDates")
    override val availableDate: String,
    @SerializedName("members")
    override val members: List<String>,
    @SerializedName("placeName")
    override val place: String,
) : WaitingPlanResponse
