package com.yapp.growth.di

import com.yapp.growth.BuildConfig
import com.yapp.growth.data.api.NetworkSettings
import com.yapp.growth.data.api.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Named("GrowthBaseUrl")
    fun provideGrowthBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun provideNetworkSettings(): NetworkSettings {
        return NetworkSettings(
            connectTimeoutMs = 1000 * 10,
            readTimeoutMs = 1000 * 10,
            isDebugMode = BuildConfig.DEBUG
        )
    }
}