package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.repository.ConfirmPlanRepository
import javax.inject.Inject

class SendConfirmPlanUseCase @Inject constructor(
    private val repository: ConfirmPlanRepository
) {
    suspend operator fun invoke(promisingId: Long, date: String): NetworkResult<Any> {
        return repository.sendConfirmPlan(promisingId, date)
    }
}
