package com.yapp.growth.domain.entity

data class Block(
    val index: Int,
    val count: Int,
    val users: List<User>,
    val color: Int
)