package com.example.to_do_list.domain.repositories

import com.example.to_do_list.data.local.LocalDataSource
import com.example.to_do_list.data.local.entities.TaskEntity
import com.example.to_do_list.data.remote.RemoteDataSource
import com.example.to_do_list.data.remote.entities.RemoteTask
import javax.inject.Inject

class LocalTaskRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
): ITaskRepository{
    override suspend fun addNewTask(taskEntity: TaskEntity) {
        localDataSource.addNewTask(taskEntity)
    }

    override suspend fun getAllTasks(): List<TaskEntity> {
        return localDataSource.getAllTasks()
    }

    override suspend fun deleteTaskById(id: Int) {
        return localDataSource.deleteTaskById(id)
    }

    override fun updateTaskStatus(taskId: Int, isCompleted: Boolean) {
        return localDataSource.updateTaskStatus(taskId, isCompleted)
    }

    override fun updateSubTasksStatus(parentId: Int, isCompleted: Boolean) {
        localDataSource.updateSubTasksStatus(parentId, isCompleted)
    }

    override fun updateTask(taskEntity: TaskEntity) {
        localDataSource.updateTasks(taskEntity)
    }

}