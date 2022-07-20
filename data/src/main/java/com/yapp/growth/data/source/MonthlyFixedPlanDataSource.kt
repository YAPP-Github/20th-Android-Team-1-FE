package com.yapp.growth.data.source

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan

interface MonthlyFixedPlanDataSource {

    suspend fun getMonthlyFixedPlanList(dateTime: String): NetworkResult<List<FixedPlan>>
}
