package com.yapp.growth.data.repository

import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import com.yapp.growth.domain.repository.DetailRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DetailRepositoryImpl @Inject constructor(
    private val dataSource: PlanzDataSource
) : DetailRepository {

    override suspend fun getFixedPlan(planId: Long): NetworkResult<FixedPlan> {
        return dataSource.getFixedPlan(planId)
    }
}
