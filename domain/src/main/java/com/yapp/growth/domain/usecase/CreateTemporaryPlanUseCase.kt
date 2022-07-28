package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TemporaryPlan
import com.yapp.growth.domain.entity.TemporaryPlanUuid
import com.yapp.growth.domain.repository.TemporaryPlanRepository
import javax.inject.Inject

class CreateTemporaryPlanUseCase @Inject constructor(
    private val repository: TemporaryPlanRepository,
) {
    suspend operator fun invoke(temporaryPlan: TemporaryPlan): NetworkResult<TemporaryPlanUuid> {
        return repository.createTemporaryPlan(temporaryPlan)
    }
}
