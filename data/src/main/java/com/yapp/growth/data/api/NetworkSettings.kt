package com.yapp.growth.data.api

data class NetworkSettings(
    val connectTimeoutMs: Long,
    val readTimeoutMs: Long,
    val isDebugMode: Boolean
)