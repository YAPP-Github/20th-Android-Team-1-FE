package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.CreateTimeTable
import com.yapp.growth.domain.entity.TimeCheckedOfDay

interface CreateTimeTableRepository {

    suspend fun getCreateTimeTable(uuid: String): NetworkResult<CreateTimeTable>

    suspend fun sendTimeCheckedOfDay(uuid: String, timeCheckedOfDays: List<TimeCheckedOfDay>): NetworkResult<Long>
}
