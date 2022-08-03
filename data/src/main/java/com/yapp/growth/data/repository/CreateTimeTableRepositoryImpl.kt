package com.yapp.growth.data.repository

import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.CreateTimeTable
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import com.yapp.growth.domain.repository.CreateTimeTableRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CreateTimeTableRepositoryImpl @Inject constructor(
    private val dataSource: PlanzDataSource
): CreateTimeTableRepository {

    override suspend fun getCreateTimeTable(uuid: String): NetworkResult<CreateTimeTable> {
        return dataSource.getCreateTimeTable(uuid)
    }

    override suspend fun makePlan(
        uuid: String,
        timeCheckedOfDays: List<TimeCheckedOfDay>
    ): NetworkResult<Long> {
        return dataSource.makePlan(uuid, timeCheckedOfDays)
    }

}
