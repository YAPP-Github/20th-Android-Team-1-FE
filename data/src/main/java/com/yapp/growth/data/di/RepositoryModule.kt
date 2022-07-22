package com.yapp.growth.data.di

import com.yapp.growth.data.repository.AllFixedPlanRepositoryImpl
import com.yapp.growth.data.repository.ConfirmPlanRepositoryImpl
import com.yapp.growth.data.repository.MonthlyFixedPlanRepositoryImpl
import com.yapp.growth.data.repository.OneDayFixedPlanRepositoryImpl
import com.yapp.growth.domain.repository.AllFixedPlanRepository
import com.yapp.growth.domain.repository.MonthlyFixedPlanRepository
import com.yapp.growth.domain.repository.OneDayFixedPlanRepository
import com.yapp.growth.domain.repository.ConfirmPlanRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindConfirmPlanRepository(confirmPlanRepository: ConfirmPlanRepositoryImpl): ConfirmPlanRepository

    @Binds
    @Singleton
    abstract fun bindOneDayFixedPlanDataRepository(OneDayPlanDataSource: OneDayFixedPlanRepositoryImpl): OneDayFixedPlanRepository

    @Binds
    @Singleton
    abstract fun bindMonthlyFixedPlanRepository(MonthlyPlanDataSource: MonthlyFixedPlanRepositoryImpl): MonthlyFixedPlanRepository

    @Binds
    @Singleton
    abstract fun bindAllFixedPlanRepository(AllPlanDataSource: AllFixedPlanRepositoryImpl): AllFixedPlanRepository
}
