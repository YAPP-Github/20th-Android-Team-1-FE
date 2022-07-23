package com.yapp.growth.data.repository

import com.yapp.growth.data.source.HomeDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import com.yapp.growth.domain.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class HomeRepositoryImpl @Inject constructor(
    private val dataSource: HomeDataSource
) : HomeRepository {

    override suspend fun getAllFixedPlanList(): NetworkResult<List<FixedPlan>> {
        return dataSource.getAllFixedPlanList()
    }
}
