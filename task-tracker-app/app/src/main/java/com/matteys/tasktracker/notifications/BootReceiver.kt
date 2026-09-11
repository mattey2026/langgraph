package com.matteys.tasktracker.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.matteys.tasktracker.data.TaskDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        val appContext = context.applicationContext
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val dao = TaskDatabase.getInstance(appContext).taskDao()
                dao.getPendingTasksWithDueDate().forEach { task ->
                    ReminderScheduler.schedule(appContext, task)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}
