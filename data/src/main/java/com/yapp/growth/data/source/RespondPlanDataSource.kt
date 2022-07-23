package com.yapp.growth.data.source

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeCheckedOfDay

interface RespondPlanDataSource {

    suspend fun sendRespondPlan(promisingId: Long, timeCheckedOfDays: List<TimeCheckedOfDay>): NetworkResult<Unit>
}
