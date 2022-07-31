package com.yapp.growth.domain.repository

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.User
import com.yapp.growth.domain.entity.UserPlanStatus

interface UserRepository {
    suspend fun signUp(): NetworkResult<User>
    suspend fun getUserInfo(): NetworkResult<User>
    fun getCachedUserInfo(): User?

    suspend fun getUserPlanStatus(planId: Long): NetworkResult<UserPlanStatus>
    suspend fun deleteUserInfo(): NetworkResult<Unit>
}
