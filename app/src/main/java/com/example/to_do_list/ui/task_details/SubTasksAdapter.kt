package com.example.to_do_list.ui.task_details


import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.R
import com.example.to_do_list.core.utils.DateTimeHelper
import com.example.to_do_list.databinding.ListItemTaskBinding
import com.example.to_do_list.domain.models.Task
import com.example.to_do_list.domain.models.TaskStatus


class SubTasksAdapter(
    private val onItemClick: (Task) -> Unit,
    private val onDeleteButtonClick: (Int) -> Unit,
    private val onUpdateButtonClick: (Task) -> Unit,
    private val onChangeStatusButtonClick: (Task, Boolean) -> Unit
) :
    RecyclerView.Adapter<SubTasksAdapter.MyViewHolder>() {

    private var tasks: MutableList<Task> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListItemTaskBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.apply {
            val task = tasks.get(position)
            tvName.text = task.name
            tvDescription.text = task.description
            tvDescription.isVisible = task.description.isNotEmpty()

            tvStartDate.text =
                holder.itemView.resources.getString(R.string.start_date)
                    .plus(": ${DateTimeHelper.format(task.startDate)}")
            tvEndDate.text =
                holder.itemView.resources.getString(R.string.end_date)
                    .plus(": ${DateTimeHelper.format(task.endDate)}")

            val taskStatus = task.getTaskStatus()
            tvStatus.text = taskStatus.name
            tvStatus.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    root.context, when (taskStatus) {
                        TaskStatus.PENDING -> R.color.gray
                        TaskStatus.IN_PROGRESS -> R.color.orange
                        TaskStatus.COMPLETED -> R.color.green
                        TaskStatus.EXPIRED -> R.color.red

                    }
                )
            )
            btnCompleteTask.isVisible = !task.isCompleted
            btnUnCompleteTask.isVisible = task.isCompleted
            btnCompleteTask.setOnClickListener {
                task.isCompleted = true
                notifyItemChanged(position)
                onChangeStatusButtonClick.invoke(task, true)
            }
            btnUnCompleteTask.setOnClickListener {
                task.isCompleted = false
                notifyItemChanged(position)
                onChangeStatusButtonClick.invoke(task, false)
            }
            btnDeleteTask.setOnClickListener {
                deleteTask(position)
                task.id?.let { it1 -> onDeleteButtonClick.invoke(it1) }
            }
            btnEditTask.setOnClickListener { onUpdateButtonClick.invoke(task) }
            root.setOnClickListener { onItemClick.invoke(task) }
        }
    }


    private fun deleteTask(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class MyViewHolder(val binding: ListItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun submitList(tasks: List<Task>) {
        this.tasks = tasks.toMutableList()
        notifyDataSetChanged()
    }
}