package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import com.yapp.growth.domain.repository.CreateTimeTableRepository
import javax.inject.Inject

class SendTimeCheckedOfDayUseCase @Inject constructor(
    private val repository: CreateTimeTableRepository
) {
    suspend operator fun invoke(uuid: String, timeCheckedOfDays: List<TimeCheckedOfDay>): NetworkResult<Long> {
        return repository.sendTimeCheckedOfDay(uuid, timeCheckedOfDays)
    }
}
