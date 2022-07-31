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

/**
 * Returns an enum entry with the specified name or `null` if no such entry was found.
 */
inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String): T? {
    return enumValues<T>().find { it.name == name }
}

inline fun <reified T : Enum<T>> String.toEnumValueOfOrNull(): T? {
    return enumValues<T>().find { it.name == this }
}
