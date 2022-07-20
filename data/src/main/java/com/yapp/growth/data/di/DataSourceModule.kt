package com.yapp.growth.data.di

import com.yapp.growth.data.source.AllFixedPlanDataSource
import com.yapp.growth.data.source.AllFixedPlanDataSourceImpl
import com.yapp.growth.data.source.ConfirmPlanDataSource
import com.yapp.growth.data.source.ConfirmPlanDataSourceImpl
import com.yapp.growth.data.source.MonthlyFixedPlanDataSource
import com.yapp.growth.data.source.MonthlyFixedPlanDataSourceImpl
import com.yapp.growth.data.source.OneDayFixedPlanDataSource
import com.yapp.growth.data.source.OneDayFixedPlanDataSourceImpl
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
    abstract fun bindOneDayFixedPlanDataSource(OneDayPlanDataSource: OneDayFixedPlanDataSourceImpl): OneDayFixedPlanDataSource

    @Binds
    @Singleton
    abstract fun bindMonthlyFixedPlanDataSource(MonthlyPlanDataSource: MonthlyFixedPlanDataSourceImpl): MonthlyFixedPlanDataSource

    @Binds
    @Singleton
    abstract fun bindAllFixedPlanDataSource(AllPlanDataSource: AllFixedPlanDataSourceImpl): AllFixedPlanDataSource
}
