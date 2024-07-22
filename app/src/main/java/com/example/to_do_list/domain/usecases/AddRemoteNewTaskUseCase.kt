package com.example.to_do_list.domain.usecases

import com.example.to_do_list.data.local.entities.TaskEntity
import com.example.to_do_list.domain.models.Task
import com.example.to_do_list.domain.repositories.ITaskRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class AddRemoteNewTaskUseCase @Inject constructor(
    @Named("Remote") private val iRemoteTaskRepository: ITaskRepository
) {

    suspend operator fun invoke(task: Task) {
        iRemoteTaskRepository.addNewTask(
            TaskEntity(
                name = task.name,
                description = task.description,
                startDate = task.startDate ?: Date(),
                endDate = task.endDate ?: Date(),
                priority = task.priority
            )
        )
    }
}