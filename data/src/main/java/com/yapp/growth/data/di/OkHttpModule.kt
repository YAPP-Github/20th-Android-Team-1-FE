package com.yapp.growth.data.di

import com.yapp.growth.data.api.ErrorResponseInterceptor
import com.yapp.growth.data.api.NetworkSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class OkHttpModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        networkSettings: NetworkSettings,
        interceptor: Interceptor,
        kakaoAccessTokenAuthenticator: Authenticator
    ): OkHttpClient {
        return getOkHttpClientBuilder(networkSettings, interceptor, kakaoAccessTokenAuthenticator).apply {
            addInterceptor(ErrorResponseInterceptor())
        }.build()
    }

    private fun getOkHttpClientBuilder(
        networkSettings: NetworkSettings,
        interceptor: Interceptor,
        tokenAuthenticator: Authenticator
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
//            addNetworkInterceptor(interceptor)
            if (networkSettings.isDebugMode) {
                addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

            authenticator(tokenAuthenticator)
            retryOnConnectionFailure(true)
            connectTimeout(networkSettings.connectTimeoutMs, TimeUnit.MILLISECONDS)
            readTimeout(networkSettings.readTimeoutMs, TimeUnit.MILLISECONDS)
        }
    }

}