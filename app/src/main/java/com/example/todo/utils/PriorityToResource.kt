package com.example.todo.utils

import androidx.core.content.res.ResourcesCompat
import com.example.todo.R

import com.example.todo.data.model.Priority

fun Priority.toResource() = when(this) {
    Priority.COMMON -> ResourcesCompat.ID_NULL
    Priority.LOW -> R.drawable.ic_priority_low_24dp
    Priority.HIGH -> R.drawable.ic_priority_high_24dp
}

fun Priority.toStringResource() = when(this) {
    Priority.COMMON -> R.string.priority_no
    Priority.LOW -> R.string.priority_low
    Priority.HIGH -> R.string.priority_high_extra
}