package com.yapp.growth.data.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.RespondUsers
import javax.inject.Inject

internal class ConfirmPlanDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi
) : ConfirmPlanDataSource {

    override suspend fun getRespondUsers(promisingKey: Long): NetworkResult<List<RespondUsers>> {
        val list = listOf<RespondUsers>()
        return NetworkResult.Success(list)
    }
}
