package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan

interface DetailRepository {

    suspend fun getFixedPlan(planId: Long): NetworkResult<Plan.FixedPlan>
}