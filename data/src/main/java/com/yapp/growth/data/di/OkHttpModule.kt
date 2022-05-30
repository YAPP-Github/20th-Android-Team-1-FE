package com.yapp.growth.data.di

import android.content.Context
import com.yapp.growth.data.api.ErrorResponseInterceptor
import com.yapp.growth.data.api.Interceptors
import com.yapp.growth.data.api.NetworkSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class OkHttpModule {

    @Singleton @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        networkSettings: NetworkSettings,
        interceptor: Interceptors
    ): OkHttpClient {
        return getOkHttpClientBuilder(context, networkSettings, interceptor).apply {
            addInterceptor(ErrorResponseInterceptor())
        }.build()
    }

    private fun getOkHttpClientBuilder(
        context: Context,
        networkSettings: NetworkSettings,
        interceptor: Interceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            addNetworkInterceptor(interceptor)
            if(networkSettings.isDebugMode) {
                addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            }

            retryOnConnectionFailure(true)
            connectTimeout(networkSettings.connectTimeoutMs, TimeUnit.MILLISECONDS)
            readTimeout(networkSettings.readTimeoutMs, TimeUnit.MILLISECONDS)
        }
    }

}