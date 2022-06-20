package com.yapp.growth

import android.content.Context
import com.yapp.growth.ui.sample.KakaoAccessToken

interface LoginSdk {

    /**
     * throws 있는 메소드는
     * 사용할 때 에러 처리를 해주어야 합니다.
     * ex) runCatching { }, mapCatching { }
     *
     */

    @Throws(AuthException::class)
    suspend fun login(context: Context): KakaoAccessToken

    @Throws(AuthException::class)
    suspend fun logout()

    @Throws(AuthException::class)
    suspend fun getAccessToken(): KakaoAccessToken?

    suspend fun isValidAccessToken(): Boolean

    suspend fun refreshToken(): Boolean

}
