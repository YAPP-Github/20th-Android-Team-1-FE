package com.yapp.growth.domain.entity

data class User(
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

