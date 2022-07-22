package com.yapp.growth.data.mapper

import com.yapp.growth.data.response.WaitingPlanResponse
import com.yapp.growth.domain.entity.Plan

fun WaitingPlanResponse.toWaitingPlan(): Plan.WaitingPlan {
    return Plan.WaitingPlan(
        id = id,
        title = title,
        isLeader = isLeader,
        category = category.keyword,
        members = members,
        startTime = 0, // TODO
        endTime = 24, // TODO
    )
}
