package com.yapp.growth.data.repository

import com.yapp.growth.data.source.ConfirmPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.RespondUsers
import com.yapp.growth.domain.repository.ConfirmPlanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ConfirmPlanRepositoryImpl @Inject constructor(
    private val dataSource: ConfirmPlanDataSource
) : ConfirmPlanRepository {

    override suspend fun getRespondUsers(promisingKey: Long): NetworkResult<RespondUsers> {
        return dataSource.getRespondUsers(promisingKey)
    }

}
