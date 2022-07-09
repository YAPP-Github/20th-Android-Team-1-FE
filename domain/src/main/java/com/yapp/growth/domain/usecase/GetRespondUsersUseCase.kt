package com.yapp.growth.domain.usecase

import com.yapp.growth.domain.repository.ConfirmPlanRepository
import com.yapp.growth.domain.repository.UserRepository
import javax.inject.Inject

class GetRespondUsersUseCase@Inject constructor(
    private val repository: ConfirmPlanRepository
) {

}
