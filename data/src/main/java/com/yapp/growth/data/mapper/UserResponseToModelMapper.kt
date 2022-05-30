package com.yapp.growth.data.mapper

import com.yapp.growth.data.response.UserResponse
import com.yapp.growth.domain.entity.User

fun UserResponse.toUser(): User {
    val response = this
    return User(
        login = response.login,
        nodeId = response.nodeId,
        avatarUrl = response.avatarUrl,
        htmlUrl = response.htmlUrl,
        name = response.name,
        location = response.location,
        blogUrl = response.blogUrl,
        publicRepos = response.publicRepos,
        followers = response.followers,
        following = response.following
    )
}

fun List<UserResponse>.toUserList(): List<User> = map {
    it.toUser()
}