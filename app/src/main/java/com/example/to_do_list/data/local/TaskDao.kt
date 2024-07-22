package com.example.to_do_list.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.to_do_list.data.local.entities.TaskEntity
import java.util.Date

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table ORDER BY priority DESC")
    fun getAllTasks(): List<TaskEntity>

    @Insert
    fun insertTask(task: TaskEntity)

    @Query("DELETE FROM task_table WHERE id =:id")
    fun deleteTaskById(id: Int)


    @Query("UPDATE task_table SET is_completed = :isCompleted WHERE id = :taskId")
    fun updateTaskStatus(taskId: Int, isCompleted: Boolean)

    @Query("UPDATE task_table SET is_completed = :isCompleted WHERE parent_id = :parentId")
    fun updateSubTasksStatus(parentId: Int, isCompleted: Boolean)

    @Query("UPDATE task_table SET name = :name,description = :description,start_date =:startDate ,end_date =:endDate, priority =:priority WHERE id = :id")
    fun updateTasks(
        id: Int,
        name: String,
        description: String,
        startDate: Date,
        endDate: Date,
        priority: Int
    )

}