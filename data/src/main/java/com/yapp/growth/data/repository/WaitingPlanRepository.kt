package com.yapp.growth.data.repository

import com.yapp.growth.data.mapper.toTemporaryPlanParameter
import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.entity.TemporaryPlan
import com.yapp.growth.domain.entity.TemporaryPlanUuid
import com.yapp.growth.domain.repository.WaitingPlanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class WaitingPlanRepositoryImpl @Inject constructor(
    private val dataSource: PlanzDataSource,
) : WaitingPlanRepository {
    override suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>> {
        return dataSource.getWaitingPlans()
    }

    override suspend fun createTemporaryPlan(
        temporaryPlan: TemporaryPlan,
    ): NetworkResult<TemporaryPlanUuid> {
        return dataSource.createTemporaryPlan(temporaryPlan.toTemporaryPlanParameter())
    }
}
