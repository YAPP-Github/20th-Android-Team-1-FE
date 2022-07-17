package com.yapp.growth.data.mapper

import com.yapp.growth.data.response.UserResponse222
import com.yapp.growth.domain.entity.TestingUser

fun mapUserDto(input: UserResponse222): TestingUser {
    return TestingUser(
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
