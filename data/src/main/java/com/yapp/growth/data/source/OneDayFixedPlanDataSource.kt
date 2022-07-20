package com.yapp.growth.data.source

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan

interface OneDayFixedPlanDataSource {

    suspend fun getOneDayFixedPlanList(dateTime: String): NetworkResult<List<FixedPlan>>
}
