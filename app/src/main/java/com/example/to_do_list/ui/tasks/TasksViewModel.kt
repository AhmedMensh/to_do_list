package com.example.to_do_list.ui.tasks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_do_list.R
import com.example.to_do_list.core.utils.UiText
import com.example.to_do_list.domain.models.Task
import com.example.to_do_list.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase,
    private val updateSubTaskStatusUseCase: UpdateSubTaskStatusUseCase,
) : ViewModel() {


    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks


    fun getAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = getAllTasksUseCase.invoke()
            withContext(Dispatchers.Main){
                _tasks.value = tasks
            }
        }
    }

    fun deleteTaskById(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase.invoke(id)
            getAllTasks()
        }
    }

    fun updateTaskStatus(task: Task,isCompleted : Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            task.id?.let { updateTaskStatusUseCase.invoke(it,isCompleted)
            updateSubTasksStatus(task.subTasks,isCompleted)
            }

        }
    }

    private fun updateSubTasksStatus(subTasks: List<Task>,isCompleted: Boolean){
        viewModelScope.launch(Dispatchers.IO) {

           subTasks.forEach {
                async { updateTaskStatusUseCase.invoke(it.id!!,isCompleted) }.await()
                updateTaskStatus(it,isCompleted)
            }
        }
    }
}