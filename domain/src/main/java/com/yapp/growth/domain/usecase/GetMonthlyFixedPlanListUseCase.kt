package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import com.yapp.growth.domain.repository.MonthlyFixedPlanRepository
import javax.inject.Inject

class GetMonthlyFixedPlanListUseCase @Inject constructor(
    private val repository: MonthlyFixedPlanRepository
) {
    suspend operator fun invoke(dateTime: String): NetworkResult<List<FixedPlan>> {
        return repository.getMonthlyFixedPlanList(dateTime)
    }
}