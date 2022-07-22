package com.yapp.growth.data.repository

import com.yapp.growth.data.source.WaitingPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.repository.WaitingPlanRepository
import javax.inject.Inject

class WaitingPlanRepositoryImpl @Inject constructor(
    private val waitingPlanDataSource: WaitingPlanDataSource,
) : WaitingPlanRepository {
    override suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>> {
        return waitingPlanDataSource.getWaitingPlans()
    }
}
