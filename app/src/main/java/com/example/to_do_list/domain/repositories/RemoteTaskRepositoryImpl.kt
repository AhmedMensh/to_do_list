package com.example.to_do_list.domain.repositories

import com.example.to_do_list.data.local.entities.TaskEntity
import com.example.to_do_list.data.remote.RemoteDataSource
import com.example.to_do_list.data.remote.entities.RemoteTask
import javax.inject.Inject

class RemoteTaskRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): ITaskRepository{
    override suspend fun addNewTask(taskEntity: TaskEntity) {
        remoteDataSource.addNewTask(taskEntity)
    }

    override suspend fun getAllTasks(): List<TaskEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTaskById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun updateTaskStatus(taskId: Int, isCompleted: Boolean) {
        TODO("Not yet implemented")
    }

    override fun updateSubTasksStatus(parentId: Int, isCompleted: Boolean) {
        TODO("Not yet implemented")
    }

    override fun updateTask(taskEntity: TaskEntity) {
        TODO("Not yet implemented")
    }


}