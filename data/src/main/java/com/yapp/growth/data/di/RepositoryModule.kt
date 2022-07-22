package com.yapp.growth.data.di

import com.yapp.growth.data.repository.ConfirmPlanRepositoryImpl
import com.yapp.growth.data.repository.CreateTimeTableRepositoryImpl
import com.yapp.growth.data.repository.WaitingPlanRepositoryImpl
import com.yapp.growth.domain.repository.ConfirmPlanRepository
import com.yapp.growth.domain.repository.CreateTimeTableRepository
import com.yapp.growth.domain.repository.WaitingPlanRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun provideWaitingPlanRepository(
        repository: WaitingPlanRepositoryImpl,
    ): WaitingPlanRepository

    @Binds
    abstract fun bindConfirmPlanRepository(
        confirmPlanRepository: ConfirmPlanRepositoryImpl
    ): ConfirmPlanRepository

    @Binds
    @Singleton
    abstract fun bindCreateTimeTableRepository(
        createTimeTableRepository: CreateTimeTableRepositoryImpl
    ): CreateTimeTableRepository
}

