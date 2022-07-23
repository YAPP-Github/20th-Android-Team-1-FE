package com.yapp.growth.data.source

import com.yapp.growth.data.parameter.TemporaryPlanParameter
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TemporaryPlanUuid

interface TemporaryPlanDataSource {
    suspend fun createTemporaryPlan(
        temporaryPlanParameter: TemporaryPlanParameter
    ): NetworkResult<TemporaryPlanUuid>
}
