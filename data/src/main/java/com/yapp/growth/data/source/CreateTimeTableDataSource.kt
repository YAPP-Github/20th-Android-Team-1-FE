package com.yapp.growth.data.source

import com.yapp.growth.data.internal.response.TimeRequestResponseImpl
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.CreateTimeTable
import com.yapp.growth.domain.entity.TimeCheckedOfDay

interface CreateTimeTableDataSource {

    suspend fun getCreateTimeTable(uuid: String): NetworkResult<CreateTimeTable>

    suspend fun sendTimeCheckedOfDay(uuid: String, timeCheckedOfDays: List<TimeCheckedOfDay>): NetworkResult<Long>
}
