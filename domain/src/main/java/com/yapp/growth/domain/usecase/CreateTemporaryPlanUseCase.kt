package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TemporaryPlan
import com.yapp.growth.domain.entity.TemporaryPlanUuid
import com.yapp.growth.domain.repository.WaitingPlanRepository
import javax.inject.Inject

class CreateTemporaryPlanUseCase @Inject constructor(
    private val waitingPlanRepository: WaitingPlanRepository,
) {
    suspend operator fun invoke(temporaryPlan: TemporaryPlan): NetworkResult<TemporaryPlanUuid> {
        return waitingPlanRepository.createTemporaryPlan(temporaryPlan)
    }
}
