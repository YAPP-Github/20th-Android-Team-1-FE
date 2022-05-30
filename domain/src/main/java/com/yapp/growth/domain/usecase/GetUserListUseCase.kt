package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.User
import com.yapp.growth.domain.repository.UserRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(): NetworkResult<List<User>> {
       return repository.getUsers()
    }
}