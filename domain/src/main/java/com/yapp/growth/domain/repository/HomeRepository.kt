package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan

interface HomeRepository {

    suspend fun getAllFixedPlanList(): NetworkResult<List<FixedPlan>>
}
