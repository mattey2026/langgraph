package com.matteys.tasktracker.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.matteys.tasktracker.data.Task

object ReminderScheduler {

    fun schedule(context: Context, task: Task) {
        val dueAt = task.dueAt ?: return
        if (dueAt <= System.currentTimeMillis()) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = buildPendingIntent(context, task)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            // Falls back to an inexact alarm if the user hasn't granted the
            // "Alarms & reminders" permission (Settings > Apps > Task Tracker).
            alarmManager.set(AlarmManager.RTC_WAKEUP, dueAt, pendingIntent)
            return
        }
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dueAt, pendingIntent)
    }

    fun cancel(context: Context, task: Task) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(buildPendingIntent(context, task))
    }

    private fun buildPendingIntent(context: Context, task: Task): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(ReminderReceiver.EXTRA_TASK_ID, task.id)
            putExtra(ReminderReceiver.EXTRA_TASK_TITLE, task.title)
        }
        return PendingIntent.getBroadcast(
            context, task.id.toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
