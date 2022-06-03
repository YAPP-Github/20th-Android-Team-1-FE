package com.kakao.beauty.data.api.internal.response

import com.kakao.beauty.data.api.response.ErrorResponse

data class ErrorResponseImpl(
    override val timestamp: Long?,
    override val status: Int?,
    override val error: String?,
    override val exception: String?,
    override val message: String?
) : ErrorResponse

