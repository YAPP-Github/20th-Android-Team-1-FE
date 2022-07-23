package com.yapp.growth.data.internal.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.data.parameter.TimeCheckedOfDayParameter
import com.yapp.growth.data.parameter.TimeCheckedOfDaysParameter
import com.yapp.growth.data.source.RespondPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import javax.inject.Inject

internal class RespondPlanDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi
): RespondPlanDataSource {

    override suspend fun sendRespondPlan(
        promisingId: Long,
        timeCheckedOfDays: List<TimeCheckedOfDay>
    ): NetworkResult<Unit> =
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
            retrofitApi.sendRespondPlan(promisingId.toString(), parameter)
        }

}
