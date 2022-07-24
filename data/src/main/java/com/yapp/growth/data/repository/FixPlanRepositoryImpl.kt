package com.yapp.growth.data.repository

import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.repository.FixPlanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FixPlanRepositoryImpl @Inject constructor(
    private val dataSource: PlanzDataSource
) : FixPlanRepository {

    override suspend fun getRespondUsers(promisingId: Long): NetworkResult<TimeTable> {
        return dataSource.getRespondUsers(promisingId)
    }

    override suspend fun sendFixPlan(promisingId: Long, date: String): NetworkResult<Any> {
        return dataSource.sendFixPlan(promisingId, date)
    }

}
