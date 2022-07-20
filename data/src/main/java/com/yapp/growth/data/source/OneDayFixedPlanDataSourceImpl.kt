package com.yapp.growth.data.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import toFixedPlan
import javax.inject.Inject

internal class OneDayFixedPlanDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi
) : OneDayFixedPlanDataSource {

    override suspend fun getOneDayFixedPlanList(dateTime: String): NetworkResult<List<FixedPlan>> =
        handleApi {
            retrofitApi.getOneDayFixedPlanList(dateTime).map {
                it.toFixedPlan()
            }
        }
}
