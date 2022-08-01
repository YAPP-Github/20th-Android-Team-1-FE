package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.User
import com.yapp.growth.domain.repository.UserRepository
import javax.inject.Inject

class ModifyNickNameUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(nickName: String): NetworkResult<User> {
        return repository.modifyNickName(nickName)
    }
}
