package com.yapp.growth.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.NetworkSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class DataModule {

    @Provides
    @Named("GrowthApiUrl")
    fun provideGrowthApiUrl(
        @Named("isDebug") debug: Boolean,
        @Named("GrowthBaseUrl") growthBaseUrl: String
    ): String =
        when (debug) {
            true -> growthBaseUrl
            else -> growthBaseUrl
        }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("GrowthApiUrl") growthApiUrl: String,
        jsonAdapterFactory: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(growthApiUrl)
            .addConverterFactory(jsonAdapterFactory)
            .build()

    @Singleton
    @Provides
    fun provideGrowthApi(
        retrofit: Retrofit
    ): GrowthApi =
        retrofit.create(GrowthApi::class.java)

    @Singleton @Provides
    fun provideJsonAdapterFactory(): Converter.Factory =
        MoshiConverterFactory.create(
            Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build())
}