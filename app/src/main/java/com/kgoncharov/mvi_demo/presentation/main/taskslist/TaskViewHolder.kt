package com.kgoncharov.mvi_demo.presentation.main.taskslist

import androidx.recyclerview.widget.RecyclerView
import com.kgoncharov.mvi_demo.data.Task
import com.kgoncharov.mvi_demo.databinding.ItemTaskBinding

class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Task, onCheckedChanged: (Int, Boolean) -> Unit) = with(binding) {
        textViewTask.text = item.text
        checkboxTask.isChecked = item.isCompleted
        checkboxTask.setOnClickListener {
            onCheckedChanged(adapterPosition, checkboxTask.isChecked)
        }
    }
}
