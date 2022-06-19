package com.kgoncharov.mvi_demo.presentation.main.taskslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kgoncharov.mvi_demo.data.Task
import com.kgoncharov.mvi_demo.databinding.ItemTaskBinding
import javax.inject.Inject

class TasksAdapter @Inject constructor() : RecyclerView.Adapter<TaskViewHolder>() {

    private val items = mutableListOf<Task>()

    var onCheckedChanged: (Int, Boolean) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(items[position], onCheckedChanged)
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<Task>) {
        val callback = TasksDiffUtilCallback(this.items, items)
        val result = DiffUtil.calculateDiff(callback)
        this.items.clear()
        this.items.addAll(items)
        result.dispatchUpdatesTo(this)
    }
}
