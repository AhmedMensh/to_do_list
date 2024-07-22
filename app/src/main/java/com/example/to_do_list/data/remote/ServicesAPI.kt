package com.example.to_do_list.data.remote

import com.example.to_do_list.data.local.entities.TaskEntity
import com.example.to_do_list.data.remote.entities.RemoteTask
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServicesAPI {

    @POST("tasks.json")
    suspend fun addNewTask(@Body taskEntity: TaskEntity)
}