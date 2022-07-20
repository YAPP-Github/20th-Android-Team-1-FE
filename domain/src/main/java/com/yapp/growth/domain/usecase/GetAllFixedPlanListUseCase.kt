package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import com.yapp.growth.domain.repository.AllFixedPlanRepository
import javax.inject.Inject

class GetAllFixedPlanListUseCase @Inject constructor(
    private val repository: AllFixedPlanRepository
) {
    suspend operator fun invoke(): NetworkResult<List<FixedPlan>> {
        return repository.getAllFixedPlanList()
    }
}