package com.yapp.growth.data.source

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan

interface WaitingPlanDataSource {
    suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>>
}
