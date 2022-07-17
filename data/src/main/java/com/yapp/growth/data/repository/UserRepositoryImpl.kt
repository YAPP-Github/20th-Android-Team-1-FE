package com.yapp.growth.data.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.data.source.UserDataSource
import com.yapp.growth.domain.entity.TestingUser
import com.yapp.growth.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource
) : UserRepository{

    override suspend fun getUsers(): NetworkResult<List<TestingUser>> = dataSource.getUsers()

}
