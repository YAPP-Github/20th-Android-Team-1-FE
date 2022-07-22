package com.yapp.growth.data.internal.response

import com.google.gson.annotations.SerializedName
import com.yapp.growth.data.response.WaitingPlanResponse

data class WaitingPlanResponseImpl(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("promisingName")
    override val title: String,
    @SerializedName("owner")
    override val leader: UserResponseImpl,
    @SerializedName("isOwner")
    override val isLeader: Boolean,
    @SerializedName("minTime")
    override val startTime: String,
    @SerializedName("maxTime")
    override val endTime: String,
    @SerializedName("category")
    override val category: CategoryResponseImpl,
    @SerializedName("availableDates")
    override val availableDate: String,
    @SerializedName("members")
    override val members: List<UserResponseImpl>,
    @SerializedName("placeName")
    override val place: String,
) : WaitingPlanResponse
