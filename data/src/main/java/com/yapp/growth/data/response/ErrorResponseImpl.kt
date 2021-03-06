package com.yapp.growth.data.response

data class ErrorResponseImpl(
    override val timestamp: Long?,
    override val status: Int?,
    override val error: String?,
    override val exception: String?,
    override val message: String?
) : ErrorResponse

