package com.yapp.growth.data.source

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.User

interface UserDataSource {

    suspend fun getUsers(): NetworkResult<List<User>>
}