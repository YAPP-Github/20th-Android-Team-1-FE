package com.yapp.growth.data.mapper

import com.yapp.growth.data.response.UserResponse
import com.yapp.growth.domain.entity.User

fun mapUserDto(input: UserResponse): User {
    return User(
        login = input.login,
        nodeId = input.nodeId,
        avatarUrl = input.avatarUrl,
        htmlUrl = input.htmlUrl,
        name = input.name,
        location = input.location,
        blogUrl = input.blogUrl,
        publicRepos = input.publicRepos,
        followers = input.followers,
        following = input.following
    )
}