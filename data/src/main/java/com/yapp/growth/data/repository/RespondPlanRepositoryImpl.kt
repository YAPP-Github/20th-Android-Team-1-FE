package com.yapp.growth.data.repository

import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import com.yapp.growth.domain.repository.RespondPlanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RespondPlanRepositoryImpl @Inject constructor(
    private val datasource: PlanzDataSource
): RespondPlanRepository {

    override suspend fun sendRespondPlan(
        planId: Long,
        timeCheckedOfDays: List<TimeCheckedOfDay>
    ): NetworkResult<Unit> {
        return datasource.sendRespondPlan(planId, timeCheckedOfDays)
    }

    override suspend fun sendRejectPlan(planId: Long): NetworkResult<Unit> {
        return datasource.sendRejectPlan(planId)
    }
}
