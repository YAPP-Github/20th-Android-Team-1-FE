package com.yapp.growth.data.internal.response

import com.google.gson.annotations.SerializedName
import com.yapp.growth.data.response.FixedPlanResponse

data class FixedPlanResponseImpl(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("promiseName")
    override val title: String,
    @SerializedName("promiseDate")
    override val date: String,
    @SerializedName("owner")
    override val leader: UserResponseImpl,
    @SerializedName("isOwner")
    override val isLeader: Boolean,
    @SerializedName("category")
    override val category: CategoryResponseImpl,
    @SerializedName("members")
    override val members: List<UserResponseImpl>,
    @SerializedName("placeName")
    override val place: String,
) : FixedPlanResponse
