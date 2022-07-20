package com.yapp.growth.data.repository

import com.yapp.growth.data.source.MonthlyFixedPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import com.yapp.growth.domain.repository.MonthlyFixedPlanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MonthlyFixedPlanRepositoryImpl @Inject constructor(
    private val dataSource: MonthlyFixedPlanDataSource
) : MonthlyFixedPlanRepository {

    override suspend fun getMonthlyFixedPlanList(dateTime: String): NetworkResult<List<FixedPlan>> {
        return dataSource.getMonthlyFixedPlanList(dateTime)
    }
}
