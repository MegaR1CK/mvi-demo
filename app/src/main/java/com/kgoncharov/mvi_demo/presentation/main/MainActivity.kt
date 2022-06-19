package com.kgoncharov.mvi_demo.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.kgoncharov.mvi_demo.databinding.ActivityMainBinding
import com.kgoncharov.mvi_demo.presentation.base.Screen
import com.kgoncharov.mvi_demo.presentation.main.taskslist.TasksAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Screen<MainScreenState> {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var adapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.bindScreen(this)
        viewModel.bindEffects(this, this)
        binding.recyclerViewTasks.adapter = adapter.apply {
            onCheckedChanged = { position, isChecked ->
                viewModel.onItemCheckedChanged(position, isChecked)
            }
        }
        binding.buttonTasksAdd.setOnClickListener { viewModel.changeAddDialogState(isVisible = true) }
    }

    override fun render(state: MainScreenState): Unit = with(binding) {
        progressTasks.isVisible = state.isLoading
        recyclerViewTasks.isVisible = !state.isLoading
        buttonTasksAdd.isVisible = !state.isLoading
        if (adapter.itemCount != state.data.size) adapter.submitList(state.data)
        if (state.isAddDialogShowing) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Add item")
                .setMessage("Do you want to add item to list?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.addItem("New task")
                }
                .setNegativeButton("No") { _, _ ->
                    viewModel.changeAddDialogState(isVisible = false)
                }
                .setOnCancelListener {
                    viewModel.changeAddDialogState(isVisible = false)
                }
                .create()
                .show()
        }
    }
}
