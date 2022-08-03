package com.yapp.growth.data.api

import com.yapp.growth.data.response.*
import com.yapp.growth.domain.NetworkResult

internal inline fun <T> handleApi(transform: () -> T): NetworkResult<T> = try {
    NetworkResult.Success(transform.invoke())
} catch (e: Exception) {
    when (e) {
        is InvalidKakaoAccessTokenException -> NetworkResult.Error(InvalidKakaoAccessTokenException(e.cause, e.url))
        is AccountNotFoundException -> NetworkResult.Error(AccountNotFoundException(e.cause, e.url))
        is BadRequestException -> NetworkResult.Error(BadRequestException(e.cause, e.url))
        is InternalServerErrorException -> NetworkResult.Error(InternalServerErrorException(e.cause, e.url))
        is ServerNotFoundException -> NetworkResult.Error(ServerNotFoundException(e.cause, e.url))
        else -> NetworkResult.Error(e)
    }
}