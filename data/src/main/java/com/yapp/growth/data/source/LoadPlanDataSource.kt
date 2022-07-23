package com.yapp.growth.data.source

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan

interface LoadPlanDataSource {
    suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>>
    suspend fun getFixedPlans(): NetworkResult<List<Plan.FixedPlan>>
}
