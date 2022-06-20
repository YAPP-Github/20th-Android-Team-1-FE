package com.yapp.growth.di

import com.yapp.growth.AuthClient
import com.yapp.growth.LoginSdk
import com.yapp.growth.kakao.KakaoAuthClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class KakaoAccessTokenAuthenticator @Inject constructor(
    private val kakaoAuthClient: KakaoAuthClient
) : Authenticator {

    private var refreshable: Boolean = true

    override fun authenticate(route: Route?, response: Response): Request? {
        if (!refreshable || response.code != 401) {
            refreshable = true
            return null
        }

        val isSuccessRefreshToken = runBlocking {
            withContext(Dispatchers.Default) {
                kakaoLoginSdk.refreshToken()
            }
        }
        refreshable = isSuccessRefreshToken

        return if (isSuccessRefreshToken) {
            response.request.newBuilder().build()
        } else {
            null
        }
    }
}