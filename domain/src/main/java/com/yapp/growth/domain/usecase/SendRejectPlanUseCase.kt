package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.repository.RespondPlanRepository
import javax.inject.Inject

class SendRejectPlanUseCase @Inject constructor(
    private val repository: RespondPlanRepository
) {
    suspend operator fun invoke(planId: Long): NetworkResult<Unit> {
        return repository.sendRejectPlan(planId)
    }
}
