package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.repository.FixPlanRepository
import javax.inject.Inject

class SendFixPlanUseCase @Inject constructor(
    private val repository: FixPlanRepository
) {
    suspend operator fun invoke(promisingId: Long, date: String): NetworkResult<Any> {
        return repository.sendFixPlan(promisingId, date)
    }
}
