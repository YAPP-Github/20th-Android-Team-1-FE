package com.yapp.growth.data.repository

import com.yapp.growth.data.source.OneDayFixedPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import com.yapp.growth.domain.repository.OneDayFixedPlanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class OneDayFixedPlanRepositoryImpl @Inject constructor(
    private val dataSource: OneDayFixedPlanDataSource
) : OneDayFixedPlanRepository {

    override suspend fun getOneDayFixedPlanList(dateTime: String): NetworkResult<List<FixedPlan>> {
        return dataSource.getOneDayFixedPlanList(dateTime)
    }
}
