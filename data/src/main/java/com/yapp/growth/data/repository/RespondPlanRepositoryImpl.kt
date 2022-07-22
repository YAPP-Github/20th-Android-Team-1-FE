package com.yapp.growth.data.repository

import com.yapp.growth.data.source.RespondPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import com.yapp.growth.domain.repository.RespondPlanRepository
import javax.inject.Inject

internal class RespondPlanRepositoryImpl @Inject constructor(
    private val datasource: RespondPlanDataSource
): RespondPlanRepository {

    override suspend fun sendRespondPlan(
        promisingId: Long,
        timeCheckedOfDays: List<TimeCheckedOfDay>
    ): NetworkResult<Unit> {
        return datasource.sendRespondPlan(promisingId, timeCheckedOfDays)
    }
}
