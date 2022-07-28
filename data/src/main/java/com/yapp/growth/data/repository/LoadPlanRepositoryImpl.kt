package com.yapp.growth.data.repository

import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.repository.LoadPlanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class LoadPlanRepositoryImpl @Inject constructor(
    private val dataSource: PlanzDataSource,
) : LoadPlanRepository {
    override suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>> {
        return dataSource.getWaitingPlans()
    }

    override suspend fun getFixedPlans(): NetworkResult<List<Plan.FixedPlan>> {
        return dataSource.getFixedPlans()
    }
}
