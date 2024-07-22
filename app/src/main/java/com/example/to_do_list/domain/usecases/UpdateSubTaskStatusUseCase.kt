package com.example.to_do_list.domain.usecases

import com.example.to_do_list.domain.models.Task
import com.example.to_do_list.domain.repositories.ITaskRepository
import javax.inject.Inject
import javax.inject.Named

class UpdateSubTaskStatusUseCase @Inject constructor(
    @Named("Local")  private val iTaskRepository: ITaskRepository
) {

    suspend operator fun invoke(parentId: Int,isCompleted : Boolean) {
         iTaskRepository.updateSubTasksStatus(parentId,isCompleted)
    }
}