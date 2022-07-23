package com.yapp.growth.data.repository

import com.yapp.growth.data.mapper.toTemporaryPlanParameter
import com.yapp.growth.data.source.TemporaryPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TemporaryPlan
import com.yapp.growth.domain.entity.TemporaryPlanUuid
import com.yapp.growth.domain.repository.TemporaryPlanRepository
import javax.inject.Inject

class TemporaryPlanRepositoryImpl @Inject constructor(
    private val dataSource: TemporaryPlanDataSource,
) : TemporaryPlanRepository {
    override suspend fun createTemporaryPlan(
        temporaryPlan: TemporaryPlan,
    ): NetworkResult<TemporaryPlanUuid> {
        return dataSource.createTemporaryPlan(temporaryPlan.toTemporaryPlanParameter())
    }
}
