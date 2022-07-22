package com.yapp.growth.data.source

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeTable

interface ConfirmPlanDataSource {

    suspend fun getRespondUsers(promisingId: Long): NetworkResult<TimeTable>

    suspend fun sendConfirmPlan(promisingId: Long, date: String): NetworkResult<Any>

}
