package com.matteys.tasktracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.matteys.tasktracker.data.Task
import com.matteys.tasktracker.notifications.ReminderScheduler
import com.matteys.tasktracker.ui.TaskViewModel
import com.matteys.tasktracker.ui.screens.AddEditTaskScreen
import com.matteys.tasktracker.ui.screens.TaskListScreen
import com.matteys.tasktracker.ui.theme.TaskTrackerTheme

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermissionIfNeeded()

        val context = this
        val repository = (application as TaskTrackerApp).repository

        setContent {
            TaskTrackerTheme {
                val viewModel: TaskViewModel = viewModel(factory = TaskViewModel.Factory(repository))
                val tasks by viewModel.tasks.collectAsState()

                var editingTask by remember { mutableStateOf<Task?>(null) }
                var showAddEdit by remember { mutableStateOf(false) }

                if (showAddEdit) {
                    AddEditTaskScreen(
                        existingTask = editingTask,
                        onSave = { task ->
                            if (editingTask == null) {
                                viewModel.addTask(task) { scheduled ->
                                    ReminderScheduler.schedule(context, scheduled)
                                }
                            } else {
                                viewModel.updateTask(task) { scheduled ->
                                    ReminderScheduler.cancel(context, scheduled)
                                    ReminderScheduler.schedule(context, scheduled)
                                }
                            }
                            showAddEdit = false
                            editingTask = null
                        },
                        onBack = {
                            showAddEdit = false
                            editingTask = null
                        }
                    )
                } else {
                    TaskListScreen(
                        tasks = tasks,
                        onAddTask = {
                            editingTask = null
                            showAddEdit = true
                        },
                        onTaskClick = { task ->
                            editingTask = task
                            showAddEdit = true
                        },
                        onToggleCompleted = { task -> viewModel.toggleCompleted(task) },
                        onDeleteTask = { task ->
                            viewModel.deleteTask(task) { deleted ->
                                ReminderScheduler.cancel(context, deleted)
                            }
                        }
                    )
                }
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
