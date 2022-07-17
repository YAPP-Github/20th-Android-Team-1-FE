package com.yapp.growth.data.response


data class UserResponse222(
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
