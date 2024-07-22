package com.example.to_do_list.ui.update_task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_do_list.R
import com.example.to_do_list.core.utils.UiText
import com.example.to_do_list.domain.models.Task
import com.example.to_do_list.domain.usecases.AddLocalNewTaskUseCase
import com.example.to_do_list.domain.usecases.AddRemoteNewTaskUseCase
import com.example.to_do_list.domain.usecases.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateTaskViewModel @Inject constructor(
    private val updateTaskUseCase: UpdateTaskUseCase,
) : ViewModel() {


    private val _error = MutableLiveData<UiText>()
    val error: LiveData<UiText> get() = _error

    fun updateTask(task: Task) {
        if (isValidTask(task)) {
            viewModelScope.launch(Dispatchers.IO) {
                updateTaskUseCase.invoke(task)
            }
        }
    }

    private fun isValidTask(task: Task): Boolean {

        if (task.name.isEmpty()) {
            _error.value = UiText.StringResource(R.string.please_enter_task_name)
            return false
        }

        if (task.startDate == null) {
            _error.value = UiText.StringResource(R.string.please_select_start_date)
            return false
        }
        if (task.endDate == null) {
            _error.value = UiText.StringResource(R.string.please_select_end_date)
            return false
        }
        return true
    }
}