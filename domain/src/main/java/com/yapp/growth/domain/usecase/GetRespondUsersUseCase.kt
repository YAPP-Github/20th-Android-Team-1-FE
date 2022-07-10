package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.RespondUsers
import com.yapp.growth.domain.repository.ConfirmPlanRepository
import javax.inject.Inject

class GetRespondUsersUseCase @Inject constructor(
    private val repository: ConfirmPlanRepository
) {
    operator suspend fun invoke(promisingKey: Long): NetworkResult<RespondUsers> {
        return repository.getRespondUsers(promisingKey)
    }

}
