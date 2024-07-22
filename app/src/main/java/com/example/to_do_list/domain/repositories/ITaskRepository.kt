package com.example.to_do_list.domain.repositories

import com.example.to_do_list.data.local.entities.TaskEntity

interface ITaskRepository {

    suspend fun addNewTask(taskEntity: TaskEntity)
    suspend fun getAllTasks() : List<TaskEntity>
    suspend fun deleteTaskById(id : Int)
    fun updateTaskStatus(taskId: Int, isCompleted: Boolean)
    fun updateSubTasksStatus(parentId: Int, isCompleted: Boolean)
    fun updateTask(taskEntity: TaskEntity)
}