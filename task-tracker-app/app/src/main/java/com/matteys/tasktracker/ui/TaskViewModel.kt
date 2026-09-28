package com.matteys.tasktracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.matteys.tasktracker.data.Task
import com.matteys.tasktracker.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val tasks: StateFlow<List<Task>> = repository.tasks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addTask(task: Task, onScheduled: (Task) -> Unit) {
        viewModelScope.launch {
            val id = repository.addTask(task)
            onScheduled(task.copy(id = id))
        }
    }

    fun updateTask(task: Task, onScheduled: (Task) -> Unit) {
        viewModelScope.launch {
            repository.updateTask(task)
            onScheduled(task)
        }
    }

    fun toggleCompleted(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun deleteTask(task: Task, onDeleted: (Task) -> Unit) {
        viewModelScope.launch {
            repository.deleteTask(task)
            onDeleted(task)
        }
    }

    class Factory(private val repository: TaskRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TaskViewModel(repository) as T
        }
    }
}
