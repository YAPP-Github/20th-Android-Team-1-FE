package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.repository.LoadPlanRepository
import javax.inject.Inject

class GetFixedPlansUseCase @Inject constructor(
    private val loadPlanRepository: LoadPlanRepository,
) {
    suspend operator fun invoke(): NetworkResult<List<Plan.FixedPlan>> {
        return loadPlanRepository.getFixedPlans()
    }
}
