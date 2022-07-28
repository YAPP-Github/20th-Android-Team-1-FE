package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Category
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.entity.TemporaryPlan
import com.yapp.growth.domain.entity.TemporaryPlanUuid

interface WaitingPlanRepository {
    suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>>

    suspend fun createTemporaryPlan(
        temporaryPlan: TemporaryPlan,
    ): NetworkResult<TemporaryPlanUuid>

    suspend fun getPlanCategories(): NetworkResult<List<Category>>
}
