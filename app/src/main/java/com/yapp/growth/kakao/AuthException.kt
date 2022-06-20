package com.yapp.growth.kakao

data class AuthException(
    override val message: String,
    val code: Int? = null,
    val url: String? = null,
    val error: Exception? = null
) : Exception((url?.let { it -> "url : $it" } ?: "") + message)

val AuthException.isAccountNotFoundError
    get() = code == 403
