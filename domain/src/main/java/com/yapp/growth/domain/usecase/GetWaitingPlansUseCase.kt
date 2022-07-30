package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.repository.WaitingPlanRepository
import javax.inject.Inject

class GetWaitingPlansUseCase @Inject constructor(
    private val waitingPlanRepository: WaitingPlanRepository,
) {
    suspend operator fun invoke(): NetworkResult<List<Plan.WaitingPlan>> {
        return waitingPlanRepository.getWaitingPlans()
    }
}
