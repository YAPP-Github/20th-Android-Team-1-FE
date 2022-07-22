package com.yapp.growth.data.di

import com.yapp.growth.data.source.ConfirmPlanDataSource
import com.yapp.growth.data.source.ConfirmPlanDataSourceImpl
import com.yapp.growth.data.source.HomeDataSource
import com.yapp.growth.data.source.HomeDataSourceImpl
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
    abstract fun bindConfirmPlanDataSource(confirmPlanDataSource: ConfirmPlanDataSourceImpl): ConfirmPlanDataSource

    @Binds
    @Singleton
    abstract fun bindAllFixedPlanDataSource(AllPlanDataSource: HomeDataSourceImpl): HomeDataSource
}
