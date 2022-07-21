package com.yapp.growth.data.internal.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.data.mapper.toTimeTable
import com.yapp.growth.data.source.ConfirmPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeTable
import javax.inject.Inject

internal class ConfirmPlanDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi
) : ConfirmPlanDataSource {

    override suspend fun getRespondUsers(promisingId: Long): NetworkResult<TimeTable> =
        handleApi {
            retrofitApi.getResponseTimeTable(promisingId.toString()).toTimeTable()
        }

}
