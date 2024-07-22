package com.example.to_do_list.data.remote

import com.example.to_do_list.data.local.entities.TaskEntity
import com.example.to_do_list.data.remote.entities.RemoteTask
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val apiService: ServicesAPI,
) {

    suspend fun addNewTask(taskEntity: TaskEntity){
        apiService.addNewTask(taskEntity)
    }
}