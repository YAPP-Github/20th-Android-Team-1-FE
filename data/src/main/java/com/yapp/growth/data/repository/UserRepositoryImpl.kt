package com.yapp.growth.data.repository

import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.User
import com.yapp.growth.domain.entity.UserPlanStatus
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserRepositoryImpl @Inject constructor(
    private val dataSource: PlanzDataSource
) : UserRepository {

    private var cachedUserInfo: User? = null

    override suspend fun signUp(): NetworkResult<User> {
        return dataSource.signUp()
    }

    override suspend fun modifyNickName(nickName: String): NetworkResult<User> {
        return dataSource.modifyNickName(nickName)
    }

    override suspend fun getUserInfo(): NetworkResult<User> {
        return dataSource.getUserInfo().onSuccess { cachedUserInfo = it }
    }

    override fun getCachedUserInfo(): User? = cachedUserInfo

    override suspend fun getUserPlanStatus(planId: Long): NetworkResult<UserPlanStatus> {
        return dataSource.getUserPlanStatus(planId)
    }

    override suspend fun deleteUserInfo(): NetworkResult<Unit> {
        return dataSource.deleteUserInfo()
    }
}
