package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeCheckedOfDay

interface RespondPlanRepository {
    suspend fun sendRespondPlan(planId: Long, timeCheckedOfDays: List<TimeCheckedOfDay>): NetworkResult<Unit>
    suspend fun sendRejectPlan(planId: Long): NetworkResult<Unit>
}
