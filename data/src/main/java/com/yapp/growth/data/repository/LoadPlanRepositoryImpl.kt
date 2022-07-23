package com.yapp.growth.data.repository

import com.yapp.growth.data.source.LoadPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.repository.LoadPlanRepository
import javax.inject.Inject

class LoadPlanRepositoryImpl @Inject constructor(
    private val loadPlanDataSource: LoadPlanDataSource,
) : LoadPlanRepository {
    override suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>> {
        return loadPlanDataSource.getWaitingPlans()
    }

    override suspend fun getFixedPlans(): NetworkResult<List<Plan.FixedPlan>> {
        return loadPlanDataSource.getFixedPlans()
    }
}
