package com.matteys.tasktracker.ui.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CategoryChip(label: String) {
    AssistChip(onClick = {}, label = { Text(label) })
}
