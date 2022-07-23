package com.yapp.growth.data.di

import com.yapp.growth.data.repository.ConfirmPlanRepositoryImpl
import com.yapp.growth.data.repository.CreateTimeTableRepositoryImpl
import com.yapp.growth.data.repository.LoadPlanRepositoryImpl
import com.yapp.growth.data.repository.RespondPlanRepositoryImpl
import com.yapp.growth.data.repository.DetailRepositoryImpl
import com.yapp.growth.data.repository.HomeRepositoryImpl
import com.yapp.growth.domain.repository.ConfirmPlanRepository
import com.yapp.growth.domain.repository.CreateTimeTableRepository
import com.yapp.growth.domain.repository.LoadPlanRepository
import com.yapp.growth.domain.repository.RespondPlanRepository
import com.yapp.growth.domain.repository.DetailRepository
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
    abstract fun bindLoadPlanRepository(
        repository: LoadPlanRepositoryImpl,
    ): LoadPlanRepository

    @Binds
    abstract fun bindConfirmPlanRepository(
        confirmPlanRepository: ConfirmPlanRepositoryImpl
    ): ConfirmPlanRepository

    @Binds
    abstract fun bindHomeRepository(
        homeRepository: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindDetailRepository(
        detailRepository: DetailRepositoryImpl
    ): DetailRepository

    @Binds
    @Singleton
    abstract fun bindCreateTimeTableRepository(
        createTimeTableRepository: CreateTimeTableRepositoryImpl
    ): CreateTimeTableRepository

    @Binds
    @Singleton
    abstract fun bindRespondPlanRepository(
        respondPlanRepository: RespondPlanRepositoryImpl
    ): RespondPlanRepository
}
