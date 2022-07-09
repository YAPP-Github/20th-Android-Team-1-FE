package com.yapp.growth.data.di

import com.yapp.growth.data.source.ConfirmPlanDataSource
import com.yapp.growth.data.source.ConfirmPlanDataSourceImpl
import com.yapp.growth.data.source.UserDataSource
import com.yapp.growth.data.source.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindUserDataSource(dataSource: UserDataSourceImpl): UserDataSource

    @Binds
    @Singleton
    abstract fun bindConfirmPlanDataSource(confirmPlanDataSource: ConfirmPlanDataSourceImpl): ConfirmPlanDataSource
}
