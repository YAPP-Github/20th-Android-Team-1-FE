package com.yapp.growth.data.internal.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.data.mapper.toFixedPlan
import com.yapp.growth.data.mapper.toWaitingPlan
import com.yapp.growth.data.source.LoadPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan
import javax.inject.Inject

class LoadPlanDataSourceImpl @Inject constructor(
    private val growthApi: GrowthApi,
): LoadPlanDataSource {
    override suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>> = handleApi {
        growthApi.getWaitingPlans().map { it.toWaitingPlan() }
    }

    override suspend fun getFixedPlans(): NetworkResult<List<Plan.FixedPlan>> = handleApi {
        growthApi.getFixedPlans().map { it.toFixedPlan() }
    }
}
