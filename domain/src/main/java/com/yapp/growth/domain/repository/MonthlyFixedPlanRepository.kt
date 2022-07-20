package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan

interface MonthlyFixedPlanRepository {

    suspend fun getMonthlyFixedPlanList(dateTime: String): NetworkResult<List<FixedPlan>>
}
