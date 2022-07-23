package com.yapp.growth.data.repository

import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.User
import com.yapp.growth.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserRepositoryImpl @Inject constructor(
    private val dataSource: PlanzDataSource
): UserRepository {

    override suspend fun signUp(): NetworkResult<User> {
        return dataSource.signUp()
    }

    override suspend fun getUserInfo(): NetworkResult<User> {
        return dataSource.getUserInfo()
    }
}
