package com.example.to_do_list.domain.usecases

import com.example.to_do_list.domain.repositories.ITaskRepository
import javax.inject.Inject
import javax.inject.Named

class UpdateTaskStatusUseCase @Inject constructor(
    @Named("Local") private val iTaskRepository: ITaskRepository
) {

    suspend operator fun invoke(taskId: Int, isCompleted: Boolean) {
        iTaskRepository.updateTaskStatus(taskId, isCompleted)
    }
}