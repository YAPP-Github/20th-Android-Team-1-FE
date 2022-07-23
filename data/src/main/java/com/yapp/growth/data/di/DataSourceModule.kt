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
    abstract fun bindConfirmPlanDataSource(
        confirmPlanDataSource: ConfirmPlanDataSourceImpl,
    ): ConfirmPlanDataSource

    @Binds
    abstract fun binDetailDataSource(
        detailDataSource: DetailDataSourceImpl,
    ): DetailDataSource

    @Binds
    abstract fun bindCreateTimeTableDataSource(
        createTimeTableDataSource: CreateTimeTableDataSourceImpl,
    ): CreateTimeTableDataSource

    @Binds
    abstract fun bindRespondPlanDataSource(
        respondPlanDataSource: RespondPlanDataSourceImpl,
    ): RespondPlanDataSource

    @Binds
    abstract fun bindLoadPlanDataSource(
        dataSource: LoadPlanDataSourceImpl,
    ): LoadPlanDataSource
}

