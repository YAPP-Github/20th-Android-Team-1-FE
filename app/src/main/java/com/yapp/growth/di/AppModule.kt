package com.yapp.growth.di

import android.content.Context
import com.yapp.growth.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Named("isDebug")
    fun provideIsDebug(): Boolean = BuildConfig.DEBUG
}