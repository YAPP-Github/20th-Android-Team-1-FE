package com.kakao.beauty.data.api.response

import java.io.IOException
import java.lang.Exception

class InvalidKakaoAccessTokenException(e: Throwable?, val url: String? = null) : IOException(e)
class AccountNotFoundException(e: Throwable?, val url: String? = null) : IOException(e)
class ServerNotFoundException(e: Throwable?, val url: String? = null) : IOException(e)
class InternalServerErrorException(e: Throwable?, val url: String? = null) : IOException(e)
class BadRequestException(e: Throwable?, val url: String? = null) : IOException(e)
