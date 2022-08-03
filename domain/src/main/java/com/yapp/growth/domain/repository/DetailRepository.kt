package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan

interface DetailRepository {
    // TODO(정호님): 삭제 요망 -> LoadPlanRepository 쪽으로 합쳐 주세요
    suspend fun getFixedPlan(planId: Long): NetworkResult<Plan.FixedPlan>
}
