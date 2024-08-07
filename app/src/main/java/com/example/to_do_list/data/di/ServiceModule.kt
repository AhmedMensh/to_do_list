package com.example.to_do_list.data.di

import com.example.to_do_list.data.remote.ServicesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideAlCoachService(retrofit: Retrofit) =
        retrofit.create(ServicesAPI::class.java)
}


