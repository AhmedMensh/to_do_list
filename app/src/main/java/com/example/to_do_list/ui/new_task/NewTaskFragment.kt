package com.example.to_do_list.ui.new_task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.to_do_list.core.utils.Constants
import com.example.to_do_list.core.utils.DateTimeHelper
import com.example.to_do_list.core.utils.UiText
import com.example.to_do_list.databinding.FragmentNewTaskBinding
import com.example.to_do_list.domain.models.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewTaskFragment : Fragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private val viewModel: NewTaskViewModel by viewModels()
    private val task by lazy { Task() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTaskPriorityMenu()
        initViewsClick()
        observeOnUIError()
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

    private fun initViewsClick() {
        binding.etStartDate.setOnClickListener {
            DateTimeHelper.showDateTimePicker(requireContext()) {
                binding.etStartDate.setText(DateTimeHelper.format(it))
                task.startDate = it
            }
        }
        binding.etEndDate.setOnClickListener {
            DateTimeHelper.showDateTimePicker(requireContext()) {
                binding.etEndDate.setText(DateTimeHelper.format(it))
                task.endDate = it
            }
        }
        binding.btnCreateTask.setOnClickListener {
            task.name = binding.etName.text.toString()
            task.description = binding.etDescription.text.toString()
            arguments?.let {
                if(it.containsKey(Constants.TASK_ID)){
                    task.parentId = it.getInt(Constants.TASK_ID)
                }
                if(it.containsKey(Constants.TASK_LEVEL)){
                    task.level = it.getInt(Constants.TASK_LEVEL,0)
                }
            }

            viewModel.addNewTask(task)

        }
    }

    private fun initTaskPriorityMenu() {
        val priorities = resources.getStringArray(com.example.to_do_list.R.array.task_priorities)
        val prioritiesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, priorities)

        prioritiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.tvPriority.setAdapter(prioritiesAdapter)
        binding.tvPriority.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                task.priority = position
            }

    }
}