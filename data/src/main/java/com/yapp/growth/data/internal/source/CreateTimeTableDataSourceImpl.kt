package com.yapp.growth.data.internal.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.data.mapper.toCreateTimeTable
import com.yapp.growth.data.mapper.toLong
import com.yapp.growth.data.parameter.TimeCheckedOfDayParameter
import com.yapp.growth.data.parameter.TimeCheckedOfDaysParameter
import com.yapp.growth.data.source.CreateTimeTableDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.CreateTimeTable
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import javax.inject.Inject

internal class CreateTimeTableDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi
): CreateTimeTableDataSource {

    override suspend fun getCreateTimeTable(uuid: String): NetworkResult<CreateTimeTable> =
        handleApi {
            retrofitApi.getCreateTimeTable(uuid).toCreateTimeTable()
        }

    override suspend fun sendTimeCheckedOfDay(
        uuid: String,
        timeCheckedOfDays: List<TimeCheckedOfDay>
    ): NetworkResult<Long> =
        handleApi {
            val parameter = TimeCheckedOfDaysParameter(
                unit = 0.5f,
                timeTable = timeCheckedOfDays.map {
                    TimeCheckedOfDayParameter(
                        date = it.date,
                        times = it.timeList
                    )
                }
            )
            retrofitApi.sendTimeCheckedOfDay(uuid, parameter).toLong()
        }
}
