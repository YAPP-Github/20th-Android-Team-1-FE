package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan

interface FixedPlanRepository {
    suspend fun getFixedPlans(): NetworkResult<List<Plan.FixedPlan>>
}
