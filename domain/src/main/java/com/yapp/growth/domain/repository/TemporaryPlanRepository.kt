package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TemporaryPlan
import com.yapp.growth.domain.entity.TemporaryPlanUuid

interface TemporaryPlanRepository {
    suspend fun createTemporaryPlan(
        temporaryPlan: TemporaryPlan,
    ): NetworkResult<TemporaryPlanUuid>
}
