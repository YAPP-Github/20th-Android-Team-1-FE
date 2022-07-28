package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.repository.FixPlanRepository
import javax.inject.Inject

class GetRespondUsersUseCase @Inject constructor(
    private val repository: FixPlanRepository
) {
    suspend operator fun invoke(planId: Long): NetworkResult<TimeTable> {
        return repository.getRespondUsers(planId)
    }

}
