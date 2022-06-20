package com.yapp.growth.di

import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import com.yapp.growth.kakao.KakaoLoginSdk
import com.yapp.growth.LoginSdk
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object KakaoModule {
    @Singleton
    @Provides
    fun provideUserApiClient(): UserApiClient = UserApiClient.instance

    @Singleton
    @Provides
    fun provideAuthApiClient(): AuthApiClient = AuthApiClient.instance

    @Singleton
    @Provides
    fun provideKakaoLoginSdk(
        userApiClient: UserApiClient,
        authApiClient: AuthApiClient
    ): LoginSdk {
        return KakaoLoginSdk(userApiClient, authApiClient)
    }
}