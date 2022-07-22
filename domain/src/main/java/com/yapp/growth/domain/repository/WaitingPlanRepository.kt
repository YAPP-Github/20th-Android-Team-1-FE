package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan

interface WaitingPlanRepository {
    suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>>
}
