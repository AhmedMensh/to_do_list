package com.example.to_do_list.data.di

import android.content.Context
import androidx.room.Room
import com.example.to_do_list.data.local.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesTasksDao(tasksDatabase: TasksDatabase) = tasksDatabase.taskDao()

    @Provides
    @Singleton
    fun providesTasksDatabase(@ApplicationContext context: Context): TasksDatabase =
        Room.databaseBuilder(context, TasksDatabase::class.java, "TasksDatabase").build()
}