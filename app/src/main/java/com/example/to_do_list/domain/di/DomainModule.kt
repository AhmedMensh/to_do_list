package com.example.to_do_list.domain.di

import com.example.to_do_list.domain.repositories.ITaskRepository
import com.example.to_do_list.domain.repositories.LocalTaskRepositoryImpl
import com.example.to_do_list.domain.repositories.RemoteTaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Singleton
    @Binds
    @Named("Local")
    abstract fun bindLocalTasksRepositoryImpl(
        tasksRepositoryImpl: LocalTaskRepositoryImpl
    ): ITaskRepository


    @Singleton
    @Binds
    @Named("Remote")
    abstract fun bindRemoteTasksRepositoryImpl(
        tasksRepositoryImpl: RemoteTaskRepositoryImpl
    ): ITaskRepository


}