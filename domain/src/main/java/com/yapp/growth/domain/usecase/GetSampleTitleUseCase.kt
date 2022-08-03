package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.repository.WaitingPlanRepository
import javax.inject.Inject

class GetSampleTitleUseCase @Inject constructor(
    private val waitingPlanRepository: WaitingPlanRepository,
) {
    suspend operator fun invoke(categoryId: Int): NetworkResult<String> {
        return waitingPlanRepository.getSampleTitle(categoryId)
    }
}
