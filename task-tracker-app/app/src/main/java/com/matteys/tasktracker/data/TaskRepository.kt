package com.matteys.tasktracker.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    val tasks: Flow<List<Task>> = dao.observeTasks()

    suspend fun addTask(task: Task): Long = dao.insert(task)
    suspend fun updateTask(task: Task) = dao.update(task)
    suspend fun deleteTask(task: Task) = dao.delete(task)
    suspend fun getTask(id: Long): Task? = dao.getById(id)
    suspend fun getPendingWithDueDate(): List<Task> = dao.getPendingTasksWithDueDate()
}
