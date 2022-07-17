package com.yapp.growth.data.source

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TestingUser

interface UserDataSource {

    suspend fun getUsers(): NetworkResult<List<TestingUser>>
}
