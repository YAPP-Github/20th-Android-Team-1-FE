package com.yapp.growth.kakao

import android.content.Context
import com.yapp.growth.AuthClient
import com.yapp.growth.UserProfile
import com.yapp.growth.ui.sample.KakaoAccessToken
import javax.inject.Inject

class KakaoAuthClient @Inject constructor(
    private val loginSdk: KakaoLoginSdk
): AuthClient {

    override suspend fun requestLogin(context: Context) {
        TODO("Not yet implemented")
    }

    override suspend fun requestLogout(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAccessToken(): KakaoAccessToken? {
        TODO("Not yet implemented")
    }

    override suspend fun isValidToken(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun refreshToken(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAuthProfile(): UserProfile {
        TODO("Not yet implemented")
    }

}