package com.yapp.growth.data.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.data.mapper.mapNullInputList
import com.yapp.growth.data.mapper.mapUserDto
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TestingUser
import javax.inject.Inject

internal class UserDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi
) : UserDataSource {

    override suspend fun getUsers(): NetworkResult<List<TestingUser>> =
        handleApi {
            mapNullInputList(retrofitApi.getUsers()) { userResponse ->
                mapUserDto(userResponse)
            }
        }

}
