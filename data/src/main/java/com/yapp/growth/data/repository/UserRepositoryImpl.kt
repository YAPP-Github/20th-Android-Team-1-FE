package com.yapp.growth.data.repository

import com.yapp.growth.data.api.handleApi
import com.yapp.growth.data.response.AccountNotFoundException
import com.yapp.growth.data.response.BadRequestException
import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.User
import com.yapp.growth.domain.entity.UserPlanStatus
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.sign

@Singleton
internal class UserRepositoryImpl @Inject constructor(
    private val dataSource: PlanzDataSource
): UserRepository {

    private var cachedUserInfo: User? = null

    override suspend fun signUp(): NetworkResult<User> {
        return dataSource.signUp()
    }

    override suspend fun getUserInfo(): NetworkResult<User> {
        return dataSource.getUserInfo().onSuccess { cachedUserInfo = it }
    }

    override fun getCachedUserInfo(): User? = cachedUserInfo

    override suspend fun getUserPlanStatus(planId: Long): NetworkResult<UserPlanStatus> {
        return dataSource.getUserPlanStatus(planId)
    }

}
