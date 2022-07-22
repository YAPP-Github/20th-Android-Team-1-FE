package com.yapp.growth.data.source

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan

interface DetailDataSource {

    suspend fun getFixedPlan(planId: Long): NetworkResult<FixedPlan>
}
