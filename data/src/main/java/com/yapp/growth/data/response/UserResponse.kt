package com.yapp.growth.data.response


data class UserResponse(
    val login: String,
    val nodeId: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val name: String,
    val location: String?,
    val blogUrl: String?,
    val publicRepos: Int,
    val followers: Int,
    val following: Int = 0
)

object UserResponsePreview {

    val user: UserResponse
        get() = UserResponse(
            login = "KwonDae",
            nodeId = "MDQ6VXNlcjMzNDQzNjYw",
            avatarUrl = "https://avatars.githubusercontent.com/u/33443660?v=4",
            htmlUrl = "https://github.com/KwonDae",
            name = "Daewon Kwon",
            location = "Seoul, South Korea",
            blogUrl = "",
            publicRepos = 16,
            followers = 29,
            following = 57
        )
}