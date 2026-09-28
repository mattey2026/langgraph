package com.matteys.tasktracker

import android.app.Application
import com.matteys.tasktracker.data.TaskDatabase
import com.matteys.tasktracker.data.TaskRepository
import com.matteys.tasktracker.notifications.NotificationHelper

class TaskTrackerApp : Application() {
    val repository: TaskRepository by lazy {
        TaskRepository(TaskDatabase.getInstance(this).taskDao())
    }

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannel(this)
    }
}
