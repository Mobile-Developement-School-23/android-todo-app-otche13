package com.example.todo.utils

import com.example.todo.R
import com.example.todo.data.model.Priority


fun Priority.toStringResource() = when(this) {
    Priority.COMMON-> R.string.priority_no
    Priority.LOW -> R.string.priority_low
    Priority.HIGH -> R.string.priority_high_extra
}