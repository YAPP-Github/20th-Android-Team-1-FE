package com.yapp.growth.data.source

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.data.mapper.toUserList
import com.yapp.growth.domain.entity.User
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi
) : UserDataSource {

    override suspend fun getUsers(): NetworkResult<List<User>> =
        handleApi {
            retrofitApi.getUsers().toUserList()
        }

}