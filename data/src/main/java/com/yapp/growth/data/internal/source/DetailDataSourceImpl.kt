package com.yapp.growth.data.internal.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.data.mapper.toFixedPlan
import com.yapp.growth.data.source.DetailDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import javax.inject.Inject

internal class DetailDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi
) : DetailDataSource {

    override suspend fun getFixedPlan(planId: Int): NetworkResult<FixedPlan> =
        handleApi {
            retrofitApi.getFixedPlan(planId).toFixedPlan()
        }
}
