package com.example.to_do_list.domain.usecases

import com.example.to_do_list.data.local.entities.TaskEntity
import com.example.to_do_list.domain.models.Task
import com.example.to_do_list.domain.repositories.ITaskRepository
import javax.inject.Inject
import javax.inject.Named

class GetAllTasksUseCase @Inject constructor(
    @Named("Local")  private val iTaskRepository: ITaskRepository
) {

    suspend operator fun invoke(): List<Task> {
        return mapToTasks(iTaskRepository.getAllTasks().map {
            Task(
                id = it.id,
                parentId = it.parentId,
                name = it.name,
                description = it.description.orEmpty(),
                startDate = it.startDate,
                endDate = it.endDate,
                isCompleted = it.isCompleted,
                level = it.level
            )
        })
    }

    private fun mapToTasks(tasks : List<Task>):  List<Task> {
        val parentTasks = tasks.filter { it.parentId == null }
        parentTasks.forEach {
            val subTasks = mutableListOf<Task>()
            getAllSubTasks(tasks,it, subTasks)
            it.subTasks = subTasks
        }
        return parentTasks
    }

    private fun getAllSubTasks(tasks : List<Task>, task: Task, into: MutableList<Task>) {
        tasks.forEach {
            if (task.id == it.parentId) {
                into.add(it)
                val subTasks = mutableListOf<Task>()
                getAllSubTasks(tasks, it, subTasks)
                it.subTasks = subTasks

            }
        }
    }
}