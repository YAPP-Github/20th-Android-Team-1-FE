package com.yapp.growth.ui.sample

import android.content.Context

interface LoginSdk {
    suspend fun login(context: Context): KakaoAccessToken
    suspend fun logout()
    suspend fun getAccessToken(): KakaoAccessToken?
    suspend fun isValidAccessToken(): Boolean
    suspend fun refreshToken(): Boolean
}

data class UserProfile(
    val nickName: String?,
    val profileImage: String?,
    val email: String
)