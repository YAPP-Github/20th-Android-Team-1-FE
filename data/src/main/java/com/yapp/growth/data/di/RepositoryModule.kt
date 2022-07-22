package com.yapp.growth.data.di

import com.yapp.growth.data.repository.ConfirmPlanRepositoryImpl
import com.yapp.growth.data.repository.HomeRepositoryImpl
import com.yapp.growth.domain.repository.ConfirmPlanRepository
import com.yapp.growth.domain.repository.HomeRepository
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
    abstract fun bindAllFixedPlanRepository(AllPlanDataSource: HomeRepositoryImpl): HomeRepository
}
