package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.RespondUsers

interface ConfirmPlanRepository {

    suspend fun getRespondUsers(promisingKey: Long): NetworkResult<List<RespondUsers>>
}
