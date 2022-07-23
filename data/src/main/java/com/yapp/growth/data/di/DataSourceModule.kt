package com.yapp.growth.data.di

import com.yapp.growth.data.internal.source.ConfirmPlanDataSourceImpl
import com.yapp.growth.data.internal.source.TemporaryPlanDataSourceImpl
import com.yapp.growth.data.internal.source.CreateTimeTableDataSourceImpl
import com.yapp.growth.data.source.ConfirmPlanDataSource
import com.yapp.growth.data.source.CreateTimeTableDataSource
import com.yapp.growth.data.source.LoadPlanDataSource
import com.yapp.growth.data.internal.source.LoadPlanDataSourceImpl
import com.yapp.growth.data.source.TemporaryPlanDataSource
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

    @Binds
    abstract fun bindTemporaryPlanDataSource(
        dataSource: TemporaryPlanDataSourceImpl,
    ): TemporaryPlanDataSource
}

