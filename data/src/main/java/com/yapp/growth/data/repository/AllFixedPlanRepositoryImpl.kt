package com.yapp.growth.data.repository

import com.yapp.growth.data.source.AllFixedPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import com.yapp.growth.domain.repository.AllFixedPlanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AllFixedPlanRepositoryImpl @Inject constructor(
    private val dataSource: AllFixedPlanDataSource
) : AllFixedPlanRepository {

    override suspend fun getAllFixedPlanList(): NetworkResult<List<FixedPlan>> {
        return dataSource.getAllFixedPlanList()
    }
}
