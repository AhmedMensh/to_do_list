package com.example.to_do_list.data.local

import androidx.room.Query
import com.example.to_do_list.data.local.entities.TaskEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val taskDao: TaskDao
) {

    fun getAllTasks(): List<TaskEntity> {
        return taskDao.getAllTasks()
    }

    fun addNewTask(taskEntity: TaskEntity) {
        taskDao.insertTask(taskEntity)
    }

    fun deleteTaskById(id: Int) {
        taskDao.deleteTaskById(id)
    }

    fun updateTaskStatus(taskId: Int, isCompleted: Boolean) {
        taskDao.updateTaskStatus(taskId, isCompleted)
    }

    fun updateSubTasksStatus(parentId: Int, isCompleted: Boolean) {
        taskDao.updateSubTasksStatus(parentId, isCompleted)
    }

    fun updateTasks(
        taskEntity: TaskEntity
    ) {
        taskDao.updateTasks(
            taskEntity.id ?: -1,
            taskEntity.name,
            taskEntity.description.orEmpty(),
            taskEntity.startDate,
            taskEntity.endDate,
            taskEntity.priority
        )
    }

}


