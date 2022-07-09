package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.RespondUsers
import com.yapp.growth.domain.repository.ConfirmPlanRepository
import javax.inject.Inject

class GetRespondUsersUseCase @Inject constructor(
    private val repository: ConfirmPlanRepository
) {
    suspend fun invoke(promisingKey: Long): NetworkResult<List<RespondUsers>> {
        return repository.getRespondUsers(promisingKey)
    }

}
