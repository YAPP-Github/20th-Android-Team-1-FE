package com.yapp.growth.data.internal.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.data.source.HomeDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import com.yapp.growth.data.mapper.toFixedPlan
import javax.inject.Inject

internal class HomeDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi
) : HomeDataSource {

    override suspend fun getAllFixedPlanList(): NetworkResult<List<FixedPlan>> =
        handleApi {
            retrofitApi.getAllFixedPlanList().map {
                it.toFixedPlan()
            }
        }
}
