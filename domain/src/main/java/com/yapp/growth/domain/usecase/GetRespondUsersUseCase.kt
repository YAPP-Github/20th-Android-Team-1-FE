package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.repository.ConfirmPlanRepository
import javax.inject.Inject

class GetRespondUsersUseCase @Inject constructor(
    private val repository: ConfirmPlanRepository
) {
    suspend operator fun invoke(promisingKey: Long): NetworkResult<TimeTable> {
        return repository.getRespondUsers(promisingKey)
    }

}
