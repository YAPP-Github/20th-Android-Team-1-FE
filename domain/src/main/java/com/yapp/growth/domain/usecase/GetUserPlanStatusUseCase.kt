package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.UserPlanStatus
import com.yapp.growth.domain.repository.UserRepository
import javax.inject.Inject

class GetUserPlanStatusUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(planId: Long): NetworkResult<UserPlanStatus> {
        return repository.getUserPlanStatus(planId)
    }
}
