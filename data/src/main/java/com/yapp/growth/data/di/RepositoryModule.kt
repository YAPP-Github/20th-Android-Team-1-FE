package com.yapp.growth.data.di

import com.yapp.growth.data.repository.UserRepositoryImpl
import com.yapp.growth.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository {
        return repository
    }
}