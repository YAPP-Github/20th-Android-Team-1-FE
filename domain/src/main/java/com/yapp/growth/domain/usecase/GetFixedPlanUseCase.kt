package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.repository.DetailRepository
import javax.inject.Inject

class GetFixedPlanUseCase @Inject constructor(
    private val repository: DetailRepository
) {
    suspend operator fun invoke(planId: Long): NetworkResult<Plan.FixedPlan> {
        return repository.getFixedPlan(planId)
    }
}