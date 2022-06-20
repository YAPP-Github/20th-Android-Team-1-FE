package com.yapp.growth

import android.content.Context
import com.yapp.growth.ui.sample.KakaoAccessToken

interface AuthClient {

    @Throws(AuthException::class)
    suspend fun requestLogin(context: Context)

    @Throws(AuthException::class)
    suspend fun requestLogout(): Boolean

    @Throws(AuthException::class)
    suspend fun getAccessToken(): KakaoAccessToken?

    suspend fun isValidToken(): Boolean

    suspend fun refreshToken(): Boolean

    @Throws(AuthException::class)
    suspend fun getAuthProfile(): UserProfile

}

data class UserProfile(
    val nickName: String?,
    val profileImage: String?,
    val email: String
)
