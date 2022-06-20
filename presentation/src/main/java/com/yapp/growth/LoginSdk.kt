package com.yapp.growth

import android.content.Context
import com.yapp.growth.ui.sample.KakaoAccessToken
import com.yapp.growth.ui.sample.KakaoRefreshToken

interface LoginSdk {
    suspend fun login(context: Context): KakaoAccessToken
    suspend fun logout()
    suspend fun getAccessToken(): KakaoAccessToken?
    suspend fun isValidAccessToken(): Boolean
    suspend fun refreshToken(): Pair<KakaoAccessToken, KakaoRefreshToken>

}
