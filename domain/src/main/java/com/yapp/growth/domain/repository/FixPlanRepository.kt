package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeTable

interface FixPlanRepository {

    suspend fun getRespondUsers(promisingId: Long): NetworkResult<TimeTable>

    suspend fun sendFixPlan(promisingId: Long, date: String): NetworkResult<Any>
}
