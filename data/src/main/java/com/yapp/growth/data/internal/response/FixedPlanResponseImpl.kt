package com.yapp.growth.data.internal.response

import com.yapp.growth.data.response.FixedPlanResponse

data class FixedPlanResponseImpl(
    override val id: Long,
    override val promiseName: String,
    override val promiseDate: String,
    override val owner: UserResponseImpl,
    override val isOwner: Boolean,
    override val category: CategoryResponseImpl,
    override val members: List<UserResponseImpl>,
    override val placeName: String
): FixedPlanResponse