package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Plan.FixedPlan
import com.yapp.growth.domain.repository.OneDayFixedPlanRepository
import javax.inject.Inject

class GetOneDayFixedPlanListUseCase @Inject constructor(
    private val repository: OneDayFixedPlanRepository
) {
    suspend operator fun invoke(dateTime: String): NetworkResult<List<FixedPlan>> {
        return repository.getOneDayFixedPlanList(dateTime)
    }
}