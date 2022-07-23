package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeCheckedOfDay

interface RespondPlanRepository {
    suspend fun sendRespondPlan(promisingId: Long, timeCheckedOfDays: List<TimeCheckedOfDay>): NetworkResult<Unit>
}
