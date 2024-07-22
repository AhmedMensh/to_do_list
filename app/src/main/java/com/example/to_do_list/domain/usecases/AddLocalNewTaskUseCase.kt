package com.example.to_do_list.domain.usecases

import com.example.to_do_list.data.local.entities.TaskEntity
import com.example.to_do_list.domain.models.Task
import com.example.to_do_list.domain.repositories.ITaskRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class AddLocalNewTaskUseCase @Inject constructor(
   @Named("Local") private val iLocalTaskRepository: ITaskRepository,
) {

    suspend operator fun invoke(task: Task) {
        iLocalTaskRepository.addNewTask(
            TaskEntity(
                name = task.name,
                description = task.description,
                startDate = task.startDate ?: Date(),
                endDate = task.endDate ?: Date(),
                priority = task.priority,
                parentId = task.parentId,
                level = task.level
            )
        )
    }
}