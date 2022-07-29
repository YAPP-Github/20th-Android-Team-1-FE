package com.yapp.growth.data.internal.response

import com.yapp.growth.data.response.UserPlanStatusResponse

data class UserPlanStatusResponseImpl(
    override val status: String

): UserPlanStatusResponse
