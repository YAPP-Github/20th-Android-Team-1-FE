package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.repository.UserRepository
import javax.inject.Inject

class RemoveCachedUserInfoUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.removeCachedUserInfo()
}