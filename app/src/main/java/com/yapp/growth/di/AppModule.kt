package com.yapp.growth.di

import com.yapp.growth.BuildConfig
import com.yapp.growth.data.api.NetworkSettings
import com.yapp.growth.LoginSdk
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Named("BaseUrl")
    @Provides
    fun provideBaseUrl(): String = "https://api.github.com"

    @Provides
    @Singleton
    fun provideNetworkSettings(): NetworkSettings {
        return NetworkSettings(
            connectTimeoutMs = 1000 * 10,
            readTimeoutMs = 1000 * 10,
            isDebugMode = BuildConfig.DEBUG
        )
    }

    @Provides
    @Singleton
    fun provideInterceptors(
        kakaoLoginSdk: LoginSdk
    ): Interceptor {
        return Interceptors(kakaoLoginSdk)
    }

    @Provides
    @Singleton
    fun provideAuthenticator(
        kakaoLoginSdk: LoginSdk
    ): Authenticator {
        return KakaoAccessTokenAuthenticator(kakaoLoginSdk)
    }
}
