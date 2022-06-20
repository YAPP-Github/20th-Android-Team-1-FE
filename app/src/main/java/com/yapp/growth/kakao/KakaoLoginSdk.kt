package com.yapp.growth.kakao

import android.content.Context
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.AccessTokenInfo
import com.yapp.growth.AuthException
import com.yapp.growth.ui.sample.KakaoAccessToken
import com.yapp.growth.LoginSdk
import com.yapp.growth.ui.sample.KakaoRefreshToken
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class KakaoLoginSdk @Inject constructor(
    private val kakaoUserApiClient: UserApiClient,
    private val kakaoAuthApiClient: AuthApiClient
) : LoginSdk {

    override suspend fun login(context: Context): KakaoAccessToken {
        val isKakaoTalkLogin =
            kakaoUserApiClient.isKakaoTalkLoginAvailable(context)
        val awaitOAuthTokenResult = suspendCancellableCoroutine<Result<OAuthToken>> { cont ->
            if (isKakaoTalkLogin)
                kakaoUserApiClient.loginWithKakaoTalk(context) { token, error ->
                    cont.resume(getOAuthTokenResult(token, error))
                }
            else
                kakaoUserApiClient.loginWithKakaoAccount(context) { token, error ->
                    cont.resume(getOAuthTokenResult(token, error))
                }
        }
        return KakaoAccessToken(awaitOAuthTokenResult.map { it.accessToken }.getOrThrow())
    }

    override suspend fun logout() {
        val awaitLogoutResult = suspendCancellableCoroutine<Result<Unit>> { cont ->
            kakaoUserApiClient.logout { e ->
                val logoutResult = when {
                    e != null -> Result.failure(e.toAuthException())
                    else -> Result.success(Unit)
                }
                cont.resume(logoutResult)
            }
        }
        awaitLogoutResult.getOrThrow()
    }

    override suspend fun getAccessToken(): KakaoAccessToken =
        KakaoAccessToken(
            kakaoAuthApiClient.tokenManagerProvider.manager.getToken()?.accessToken
                ?: throw AuthException("token is null")
        )

    override suspend fun isValidAccessToken(): Boolean {
        return awaitTokenInfoResult().isSuccess
    }

    override suspend fun refreshToken(): Pair<KakaoAccessToken, KakaoRefreshToken> {
        val awaitRefreshTokenResult = suspendCancellableCoroutine<Result<OAuthToken>> { cont ->
            kakaoAuthApiClient.refreshToken { token, e ->
                cont.resume(getOAuthTokenResult(token, e))
            }
        }
        return awaitRefreshTokenResult.mapCatching { token -> KakaoAccessToken(token.accessToken) to KakaoRefreshToken(token.refreshToken) }.getOrThrow()
    }

    private val awaitTokenInfoResult: suspend () -> Result<AccessTokenInfo> = {
        suspendCoroutine { cont ->
            kakaoUserApiClient.accessTokenInfo { tokenInfo, e ->
                val getAccessTokenInfoResult = when {
                    e != null -> Result.failure(e.toAuthException())
                    tokenInfo == null -> Result.failure(AuthException("Token info is null"))
                    tokenInfo.expiresIn <= 0 -> Result.failure(AuthException("token expiresIn under 0"))
                    else -> Result.success(tokenInfo)
                }
                cont.resume(getAccessTokenInfoResult)
            }
        }
    }

    private val getOAuthTokenResult: (OAuthToken?, Throwable?) -> Result<OAuthToken> = { token, e ->
        when {
            token != null -> Result.success(token)
            e != null -> Result.failure(e.toAuthException())
            else -> Result.failure(AuthException("token and error is null"))
        }
    }

    private fun Throwable?.toAuthException(): AuthException =
        AuthException(this?.message ?: "error message is null")

}