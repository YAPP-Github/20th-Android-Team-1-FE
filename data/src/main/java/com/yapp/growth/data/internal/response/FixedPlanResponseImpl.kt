package com.yapp.growth.data.internal.response

import com.squareup.moshi.Json
import com.yapp.growth.data.response.FixedPlanResponse

data class FixedPlanResponseImpl(
    @Json(name = "id")
    override val id: Int,
    @Json(name = "promiseName")
    override val title: String,
    @Json(name = "promiseDate")
    override val date: String,
    @Json(name = "owner")
    override val leader: UserResponseImpl,
    @Json(name = "isOwner")
    override val isLeader: Boolean,
    @Json(name = "category")
    override val category: CategoryResponseImpl,
    @Json(name = "members")
    override val members: List<UserResponseImpl>,
    @Json(name = "placeName")
    override val place: String,
) : FixedPlanResponse
