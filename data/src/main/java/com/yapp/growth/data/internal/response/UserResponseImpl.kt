package com.yapp.growth.data.internal.response

import com.yapp.growth.data.response.UserResponse

data class UserResponseImpl (
    override val id: Long,
    override val userName: String
): UserResponse
