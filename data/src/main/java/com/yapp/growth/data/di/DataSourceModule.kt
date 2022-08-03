package com.yapp.growth.data.di

import com.yapp.growth.data.internal.source.*
import com.yapp.growth.data.source.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindPlanzDataSource(
        dataSource: PlanzDataSourceImpl
    ): PlanzDataSource

}

