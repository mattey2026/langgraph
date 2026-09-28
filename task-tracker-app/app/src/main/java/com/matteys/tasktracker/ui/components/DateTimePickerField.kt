package com.matteys.tasktracker.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DateTimePickerField(
    dueAt: Long?,
    onDueAtChange: (Long?) -> Unit
) {
    val context = LocalContext.current
    val label = dueAt?.let {
        SimpleDateFormat("MMM d, yyyy 'at' h:mm a", Locale.getDefault()).format(Date(it))
    } ?: "Set a due date (optional)"

    AssistChip(
        onClick = {
            val calendar = Calendar.getInstance().apply {
                dueAt?.let { timeInMillis = it }
            }
            DatePickerDialog(
                context,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            calendar.set(Calendar.HOUR_OF_DAY, hour)
                            calendar.set(Calendar.MINUTE, minute)
                            calendar.set(Calendar.SECOND, 0)
                            onDueAtChange(calendar.timeInMillis)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false
                    ).show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        },
        label = { Text(label) }
    )
}
