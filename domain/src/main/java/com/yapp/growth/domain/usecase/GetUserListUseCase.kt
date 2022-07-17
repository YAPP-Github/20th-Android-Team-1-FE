package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TestingUser
import com.yapp.growth.domain.repository.UserRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(): NetworkResult<List<TestingUser>> {
       return repository.getUsers()
    }
}
