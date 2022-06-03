package com.kakao.beauty.data.api.response

interface ErrorResponse {
    val timestamp: Long?
    val status: Int?
    val error: String?
    val exception: String?
    val message: String?
}

