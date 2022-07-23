package com.yapp.growth.data.repository

import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.repository.ConfirmPlanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ConfirmPlanRepositoryImpl @Inject constructor(
    private val dataSource: PlanzDataSource
) : ConfirmPlanRepository {

    override suspend fun getRespondUsers(promisingId: Long): NetworkResult<TimeTable> {
        return dataSource.getRespondUsers(promisingId)
    }

    override suspend fun sendConfirmPlan(promisingId: Long, date: String): NetworkResult<Any> {
        return dataSource.sendConfirmPlan(promisingId, date)
    }

}
