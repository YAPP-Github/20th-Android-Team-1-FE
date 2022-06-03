package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.User

interface UserRepository {

    suspend fun getUsers(): NetworkResult<List<User>>
}