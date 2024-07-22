package com.example.to_do_list.ui.update_task

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.to_do_list.R
import com.example.to_do_list.core.utils.Constants
import com.example.to_do_list.core.utils.DateTimeHelper
import com.example.to_do_list.core.utils.UiText
import com.example.to_do_list.databinding.FragmentUpdateTaskBinding
import com.example.to_do_list.domain.models.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateTaskFragment : Fragment() {

    private lateinit var binding: FragmentUpdateTaskBinding
    private val viewModel : UpdateTaskViewModel by viewModels()
    private var task: Task? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTaskPriorityMenu()
        getTask()
        observeOnUIError()
        initViewsClick()
    }

    private fun initViewsClick(){
        binding.etStartDate.setOnClickListener {
            DateTimeHelper.showDateTimePicker(requireContext()) {
                binding.etStartDate.setText(DateTimeHelper.format(it))
                task?.startDate = it
            }
        }
        binding.etEndDate.setOnClickListener {
            DateTimeHelper.showDateTimePicker(requireContext()) {
                binding.etEndDate.setText(DateTimeHelper.format(it))
                task?.endDate = it
            }
        }
        binding.btnUpdateTask.setOnClickListener {
            task?.name = binding.etName.text.toString()
            task?.description = binding.etDescription.text.toString()
            task?.let { it -> viewModel.updateTask(it) }
        }
    }

    private fun observeOnUIError() {
        viewModel.error.observe(viewLifecycleOwner) {
            when (it) {
                is UiText.StringResource -> {
                    Toast.makeText(requireContext(), "${getString(it.resId)}", Toast.LENGTH_SHORT)
                        .show()
                }
                is UiText.DynamicString -> {
                    Toast.makeText(requireContext(), "${it.value}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun initTaskPriorityMenu() {
        val priorities = resources.getStringArray(R.array.task_priorities)
        val prioritiesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, priorities)

        prioritiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tvPriority.setAdapter(prioritiesAdapter)
        binding.tvPriority.setText(prioritiesAdapter.getItem(task?.priority ?: 0), false)
        binding.tvPriority.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                task?.priority = position
            }

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
            task?.let { it1 -> bindTaskData(it1) }
        }
    }

    private fun bindTaskData(task: Task) {
        binding.etName.setText(task.name)
        binding.etDescription.setText(task.description)
        binding.etStartDate.setText(DateTimeHelper.format(task.startDate))
        binding.etEndDate.setText(DateTimeHelper.format(task.endDate))

    }
}