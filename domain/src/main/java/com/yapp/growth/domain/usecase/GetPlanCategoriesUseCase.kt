package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Category
import com.yapp.growth.domain.repository.WaitingPlanRepository
import javax.inject.Inject

class GetPlanCategoriesUseCase @Inject constructor(
    private val waitingPlanRepository: WaitingPlanRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Category>> {
        return waitingPlanRepository.getPlanCategories()
    }
}
