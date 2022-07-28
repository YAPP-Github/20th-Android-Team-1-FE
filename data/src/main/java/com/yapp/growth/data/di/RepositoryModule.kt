package com.yapp.growth.data.di

import com.yapp.growth.data.repository.FixPlanRepositoryImpl
import com.yapp.growth.data.repository.CreateTimeTableRepositoryImpl
import com.yapp.growth.data.repository.LoadPlanRepositoryImpl
import com.yapp.growth.data.repository.TemporaryPlanRepositoryImpl
import com.yapp.growth.domain.repository.FixPlanRepository
import com.yapp.growth.domain.repository.TemporaryPlanRepository
import com.yapp.growth.domain.repository.CreateTimeTableRepository
import com.yapp.growth.domain.repository.LoadPlanRepository
import com.yapp.growth.data.repository.*
import com.yapp.growth.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindLoadPlanRepository(
        repository: LoadPlanRepositoryImpl,
    ): LoadPlanRepository

    @Binds
    abstract fun bindTemporaryPlanRepository(
        repository: TemporaryPlanRepositoryImpl,
    ): TemporaryPlanRepository

    @Binds
    abstract fun bindFixPlanRepository(
        fixPlanRepository: FixPlanRepositoryImpl,
    ): FixPlanRepository

    @Binds
    abstract fun bindDetailRepository(
        detailRepository: DetailRepositoryImpl,
    ): DetailRepository

    @Binds
    abstract fun bindCreateTimeTableRepository(
        createTimeTableRepository: CreateTimeTableRepositoryImpl,
    ): CreateTimeTableRepository

    @Binds
    abstract fun bindRespondPlanRepository(
        respondPlanRepository: RespondPlanRepositoryImpl,
    ): RespondPlanRepository

    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository
}
