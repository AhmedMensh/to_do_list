package com.example.to_do_list.domain.models

import android.os.Parcelable
import android.util.Log
import com.example.to_do_list.core.utils.DateTimeHelper
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Task(
    val id: Int? = null,
    var name: String = "",
    var parentId: Int? = null,
    var description: String = "",
    var startDate: Date? = null,
    var endDate: Date? = null,
    var isCompleted: Boolean = false,
    var priority: Int = 0,
    var level: Int = 0,
    var subTasks: List<Task> = emptyList(),
) : Parcelable {


    fun getTaskStatus(): TaskStatus {
        if (DateTimeHelper.isBeforeCurrentDate(endDate)) return TaskStatus.EXPIRED

        if (subTasks.isEmpty()) {
            if (isCompleted) return TaskStatus.COMPLETED
            return TaskStatus.PENDING
        }

        return getSubTasksStatus(this,subTasks,)
    }

    private fun getSubTasksStatus(
        task: Task,
        subTasks: List<Task>,
        set: HashSet<TaskStatus> = hashSetOf()
    ): TaskStatus {

        subTasks.forEach {
            if (DateTimeHelper.isBeforeCurrentDate(endDate)) {
                set.add(TaskStatus.EXPIRED)
            } else if (it.isCompleted){
                if(task.isCompleted)  set.add(TaskStatus.COMPLETED)
                else set.add(TaskStatus.IN_PROGRESS)
            }

            else set.add(TaskStatus.PENDING)

            if (set.size == 2) return TaskStatus.IN_PROGRESS
            if (it.subTasks.isNotEmpty())
                return getSubTasksStatus(it,it.subTasks, set)
        }

        return set.first()
    }
}