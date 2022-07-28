package com.yapp.growth.data.mapper

import com.yapp.growth.data.response.UserResponse
import com.yapp.growth.domain.entity.User

fun List<UserResponse>.toUserList(): List<User> = map { it.toUser() }
fun UserResponse.toUser(): User {
    val response = this
    return User (
        id = response.id,
        userName = response.userName
    )
}
