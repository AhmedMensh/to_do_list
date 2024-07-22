package com.example.to_do_list.domain.usecases

import com.example.to_do_list.data.local.entities.TaskEntity
import com.example.to_do_list.domain.models.Task
import com.example.to_do_list.domain.repositories.ITaskRepository
import javax.inject.Inject
import javax.inject.Named

class DeleteTaskUseCase @Inject constructor(
    @Named("Local")  private val iTaskRepository: ITaskRepository
) {

    suspend operator fun invoke(id: Int) {
        iTaskRepository.deleteTaskById(id)
    }
}