package com.yapp.growth.data.source

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.RespondUsers

interface ConfirmPlanDataSource {

    suspend fun getRespondUsers(promisingKey: Long): NetworkResult<List<RespondUsers>>
}
