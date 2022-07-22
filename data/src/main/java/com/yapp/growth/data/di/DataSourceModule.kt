package com.yapp.growth.data.di

import com.yapp.growth.data.internal.source.ConfirmPlanDataSourceImpl
import com.yapp.growth.data.internal.source.CreateTimeTableDataSourceImpl
import com.yapp.growth.data.source.ConfirmPlanDataSource
import com.yapp.growth.data.source.CreateTimeTableDataSource
import com.yapp.growth.data.source.LoadPlanDataSource
import com.yapp.growth.data.internal.source.LoadPlanDataSourceImpl
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
    abstract fun bindCreateTimeTableDataSource(createTimeTableDataSource: CreateTimeTableDataSourceImpl): CreateTimeTableDataSource

    @Binds
    abstract fun bindLoadPlanDataSource(
        dataSource: LoadPlanDataSourceImpl,
    ): LoadPlanDataSource
}

