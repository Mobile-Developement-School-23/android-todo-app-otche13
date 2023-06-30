package com.example.todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDateTime
import java.util.Date

@Entity
data class TodoItem(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") val id: String,
    @SerializedName("description") val description: String,
    @SerializedName("priority")val priority: Priority = Priority.NO,
    @SerializedName("deadline")val deadline: String? = null,
    @SerializedName("isDone")val isDone: Boolean = false,
    @SerializedName("createdAt")val createdAt: String = LocalDateTime.now().toString(),
    @SerializedName("editedAt")val editedAt: String? = null,
    @SerializedName("last_updated_by") val lastUpdatedBy: String? = "model",
    @SerializedName("color") val color: String? = "#FFFFFF",
)


