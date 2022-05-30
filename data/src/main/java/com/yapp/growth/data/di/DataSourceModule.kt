package com.yapp.growth.data.di

import com.yapp.growth.data.source.UserDataSource
import com.yapp.growth.data.source.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DataSourceModule {

    @Provides
    @Singleton
    fun bindUserDataSource(dataSource: UserDataSourceImpl): UserDataSource {
        return dataSource
    }
}