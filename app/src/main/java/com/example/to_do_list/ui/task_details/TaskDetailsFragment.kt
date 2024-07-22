package com.example.to_do_list.ui.task_details

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.to_do_list.R
import com.example.to_do_list.core.utils.Constants
import com.example.to_do_list.databinding.FragmentTaskDetailsBinding
import com.example.to_do_list.domain.models.Task
import com.example.to_do_list.ui.tasks.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailsBinding

    //    private val viewModel: TasksViewModel by viewModels()
    private val viewModel: TasksViewModel by viewModels()
    private val subTasksAdapter = SubTasksAdapter(
        ::navigateToTaskDetails,
        ::deleteTask,
        ::navigateToUpdateTask,
        ::updateTaskStatus
    )
    private var task: Task? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTask()
        initViewsClick()
        binding.rvSubtasks.adapter = subTasksAdapter
    }

    private fun navigateToTaskDetails(task: Task) {
        findNavController().navigate(
            R.id.taskDetailsFragment,
            bundleOf(Constants.TASK to task)
        )
    }
    private fun navigateToUpdateTask(task: Task) {
        findNavController().navigate(
            R.id.updateTaskFragment,
            bundleOf(Constants.TASK to task)
        )
    }

    private fun getTask() {
        arguments?.let {
            task = when {
                Build.VERSION.SDK_INT >= 33 -> it.getParcelable(
                    Constants.TASK,
                    Task::class.java
                )

                else -> @Suppress("DEPRECATION") it.getParcelable(Constants.TASK) as? Task
            }
            Log.d("TAG", "getTask: $task")
            binding.btnCreateSubTask.isVisible = (task?.level ?: 0) < 2
            task?.subTasks?.let { it1 -> subTasksAdapter.submitList(it1) }
        }
    }

    private fun initViewsClick() {
        binding.btnCreateSubTask.setOnClickListener {
            findNavController().navigate(
                R.id.newTaskFragment, bundleOf(
                    Constants.TASK_ID to task?.id,
                    Constants.TASK_LEVEL to task?.level?.plus(1)
                )
            )
        }
    }
    private fun updateTaskStatus(task: Task,isCompleted : Boolean){
        viewModel.updateTaskStatus(task, isCompleted)
    }

    private fun deleteTask(taskId: Int){
        viewModel.deleteTaskById(taskId)
    }
}