package com.yapp.growth.data.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import toFixedPlan
import javax.inject.Inject

internal class MonthlyFixedPlanDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi
) : MonthlyFixedPlanDataSource {

    override suspend fun getMonthlyFixedPlanList(dateTime: String): NetworkResult<List<FixedPlan>> =
        handleApi {
            retrofitApi.getMonthlyFixedPlanList(dateTime).map {
                it.toFixedPlan()
            }
        }
}
