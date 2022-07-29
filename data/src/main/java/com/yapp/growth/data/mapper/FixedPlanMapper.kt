package com.yapp.growth.data.mapper

import com.yapp.growth.data.response.FixedPlanResponse
import com.yapp.growth.domain.entity.Plan

fun FixedPlanResponse.toFixedPlan(): Plan.FixedPlan {
    return Plan.FixedPlan(
        id = id,
        title = title,
        isLeader = isLeader,
        category = category.toCategory(),
        members = members.map { it.userName },
        place = place,
        date = date,
    )
}
