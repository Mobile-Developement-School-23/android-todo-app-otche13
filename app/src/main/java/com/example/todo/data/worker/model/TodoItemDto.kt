package com.example.todo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo.data.repo.mapper.mapToString
import com.example.todo.data.repo.mapper.toTimestamp
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@Entity(tableName = "task")
data class TodoItemDto(
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerialName("id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "text")
    @SerialName("text")
    val description: String,

    @ColumnInfo(name = "deadline")
    @SerialName("deadline")
    val deadline: Long? = null,

    @ColumnInfo(name = "importance")
    @SerialName("importance")
    val priority: String = Priority.COMMON.mapToString(),

    @ColumnInfo(name = "done")
    @SerialName("done")
    val isDone: Boolean = false,

    @ColumnInfo(name = "color")
    @SerialName("color")
    val color: String? = null,

    @ColumnInfo(name = "created_at")
    @SerialName("created_at")
    val createdAt: Long = LocalDateTime.now().toTimestamp(),

    @ColumnInfo(name = "changed_at")
    @SerialName("changed_at")
    val editedAt: Long = LocalDateTime.now().toTimestamp(),

    @ColumnInfo(name = "last_updated_by")
    @SerialName("last_updated_by")
    val lastUpdatedBy: String = "1110111"
)
