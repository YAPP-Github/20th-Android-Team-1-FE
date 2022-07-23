package com.yapp.growth.data.di

import com.yapp.growth.data.internal.source.CreateTimeTableDataSourceImpl
import com.yapp.growth.data.internal.source.RespondPlanDataSourceImpl
import com.yapp.growth.data.internal.source.ConfirmPlanDataSourceImpl
import com.yapp.growth.data.internal.source.CreateTimeTableDataSourceImpl
import com.yapp.growth.data.source.ConfirmPlanDataSource
import com.yapp.growth.data.internal.source.DetailDataSourceImpl
import com.yapp.growth.data.internal.source.HomeDataSourceImpl
import com.yapp.growth.data.source.CreateTimeTableDataSource
import com.yapp.growth.data.source.LoadPlanDataSource
import com.yapp.growth.data.internal.source.LoadPlanDataSourceImpl
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
    abstract fun bindConfirmPlanDataSource(
        confirmPlanDataSource: ConfirmPlanDataSourceImpl
    ): ConfirmPlanDataSource

    @Binds
    abstract fun bindHomeDataSource(
        homeDataSource: HomeDataSourceImpl
    ): HomeDataSource

    @Binds
    abstract fun binDetailDataSource(
        detailDataSource: DetailDataSourceImpl
    ): DetailDataSource

    @Binds
    abstract fun bindCreateTimeTableDataSource(
        createTimeTableDataSource: CreateTimeTableDataSourceImpl
    ): CreateTimeTableDataSource

    @Binds
    abstract fun bindLoadPlanDataSource(
        dataSource: LoadPlanDataSourceImpl,
    ): LoadPlanDataSource

    @Binds
    abstract fun bindRespondPlanDataSource(
        respondPlanDataSource: RespondPlanDataSourceImpl
    ): RespondPlanDataSource
}

