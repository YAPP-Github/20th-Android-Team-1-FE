package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.CreateTimeTable
import com.yapp.growth.domain.repository.CreateTimeTableRepository
import javax.inject.Inject

class GetCreateTimeTableUseCase @Inject constructor(
    private val repository: CreateTimeTableRepository
) {
    suspend operator fun invoke(uuid: String): NetworkResult<CreateTimeTable> {
        return repository.getCreateTimeTable(uuid)
    }
}
