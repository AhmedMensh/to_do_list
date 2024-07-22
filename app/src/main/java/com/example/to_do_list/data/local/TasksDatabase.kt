package com.example.to_do_list.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.to_do_list.data.local.converters.DateConverter
import com.example.to_do_list.data.local.entities.TaskEntity


@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}

