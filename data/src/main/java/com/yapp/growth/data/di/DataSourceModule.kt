package com.yapp.growth.data.di

import com.yapp.growth.data.internal.source.CreateTimeTableDataSourceImpl
import com.yapp.growth.data.internal.source.RespondPlanDataSourceImpl
import com.yapp.growth.data.internal.source.ConfirmPlanDataSourceImpl
import com.yapp.growth.data.internal.source.DetailDataSourceImpl
import com.yapp.growth.data.internal.source.HomeDataSourceImpl
import com.yapp.growth.data.source.CreateTimeTableDataSource
import com.yapp.growth.data.source.RespondPlanDataSource
import com.yapp.growth.data.source.ConfirmPlanDataSource
import com.yapp.growth.data.source.DetailDataSource
import com.yapp.growth.data.source.HomeDataSource
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
    abstract fun bindHomeDataSource(homeDataSource: HomeDataSourceImpl): HomeDataSource

    @Binds
    @Singleton
    abstract fun binDetailDataSource(detailDataSource: DetailDataSourceImpl): DetailDataSource

    @Binds
    @Singleton
    abstract fun bindCreateTimeTableDataSource(createTimeTableDataSource: CreateTimeTableDataSourceImpl): CreateTimeTableDataSource

    @Binds
    @Singleton
    abstract fun bindRespondPlanDataSource(respondPlanDataSource: RespondPlanDataSourceImpl): RespondPlanDataSource
}
