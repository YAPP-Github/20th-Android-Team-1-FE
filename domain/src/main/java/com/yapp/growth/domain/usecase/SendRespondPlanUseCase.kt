package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import com.yapp.growth.domain.repository.RespondPlanRepository
import javax.inject.Inject

class SendRespondPlanUseCase @Inject constructor(
    private val repository: RespondPlanRepository
) {

    suspend operator fun invoke(promisingId: Long, timeCheckedOfDays: List<TimeCheckedOfDay>): NetworkResult<Unit> {
        return repository.sendRespondPlan(promisingId, timeCheckedOfDays)
    }
}
