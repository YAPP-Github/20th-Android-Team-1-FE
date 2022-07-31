package com.yapp.growth.data.repository

import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.repository.FixedPlanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FixedPlanRepositoryImpl @Inject constructor(
    private val dataSource: PlanzDataSource,
) : FixedPlanRepository {
    override suspend fun getFixedPlans(): NetworkResult<List<Plan.FixedPlan>> {
        return dataSource.getFixedPlans()
    }
}
