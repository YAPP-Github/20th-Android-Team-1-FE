package com.yapp.growth.data.response

interface ErrorResponse {
    val timestamp: Long?
    val status: Int?
    val error: String?
    val exception: String?
    val message: String?
}

