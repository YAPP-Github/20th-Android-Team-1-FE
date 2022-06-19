package com.yapp.growth.di

import com.yapp.growth.ui.sample.LoginSdk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class Interceptors @Inject constructor(
    private val kakaoLoginSdk: LoginSdk
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            withContext(Dispatchers.Default) {
                runCatching { kakaoLoginSdk.getAccessToken() }
            }
        }

        val request = chain.request().newBuilder().apply {
            accessToken
                .onSuccess { result ->
                    val token = result?.accessToken
                    if (token.isNullOrBlank().not())
                        addHeader("Authorization", "Bearer $token")
                }
            addHeader("Accept", "application/json")
        }
        return chain.proceed(request.build())
    }

}