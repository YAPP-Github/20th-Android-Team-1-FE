package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.CreateTimeTable

interface CreateTimeTableRepository {

    suspend fun getCreateTimeTable(uuid: String): NetworkResult<CreateTimeTable>
}
