package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.entity.TimeTable

interface FixPlanRepository {

    suspend fun getRespondUsers(planId: Long): NetworkResult<TimeTable>

    suspend fun sendFixPlan(planId: Long, date: String): NetworkResult<Plan.FixedPlan>
}
