package com.yapp.growth.data.di

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
    abstract fun bindWaitingPlanRepository(
        repository: FixedPlanRepositoryImpl,
    ): FixedPlanRepository

    @Binds
    abstract fun bindFixedPlanRepository(
        repository: WaitingPlanRepositoryImpl,
    ): WaitingPlanRepository

    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepositoryImpl,
    ): UserRepository

    // TODO: 삭제
    @Binds
    abstract fun bindFixPlanRepository(
        fixPlanRepository: FixPlanRepositoryImpl,
    ): FixPlanRepository

    // TODO: 삭제
    @Binds
    abstract fun bindDetailRepository(
        detailRepository: DetailRepositoryImpl,
    ): DetailRepository

    // TODO: 삭제
    @Binds
    abstract fun bindCreateTimeTableRepository(
        createTimeTableRepository: CreateTimeTableRepositoryImpl,
    ): CreateTimeTableRepository

    // TODO: 삭제
    @Binds
    abstract fun bindRespondPlanRepository(
        respondPlanRepository: RespondPlanRepositoryImpl,
    ): RespondPlanRepository
}
