package com.example.to_do_list.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.to_do_list.R
import com.example.to_do_list.core.utils.Constants
import com.example.to_do_list.databinding.FragmentTasksBinding
import com.example.to_do_list.domain.models.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private val viewModel: TasksViewModel by viewModels()
    private val tasksAdapter by lazy {
        TasksAdapter(
            ::navigateToTaskDetails,
            viewModel::deleteTaskById,
            ::navigateToUpdateTask,
            viewModel::updateTaskStatus
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTasksRecycler()
        collectTasksData()
        initViewsClick()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllTasks()
    }
    private fun initViewsClick() {
        binding.btnNewTask.setOnClickListener {
            findNavController().navigate(
                R.id.newTaskFragment,
                bundleOf(Constants.TASK_LEVEL to 0)
            )
        }
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
    private fun initTasksRecycler() {
        binding.rvTasks.adapter = tasksAdapter
    }

    private fun collectTasksData() {
        viewModel.tasks.observe(viewLifecycleOwner) {
            it?.let {
                tasksAdapter.submitList(it)
            }
        }
    }
}