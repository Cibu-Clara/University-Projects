package com.example.taskapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.taskapp.Constants

@Entity(tableName = Constants.TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class Task(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "priorityLevel") val priorityLevel: String,
    @ColumnInfo(name = "dateDeadline") val dateDeadline: String,
    @ColumnInfo(name = "timeDeadline") val timeDeadline: String,
    @ColumnInfo(name = "description") val description: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)

val placeHolderList = listOf(Task(
    title = "No tasks found",
    priorityLevel = "None",
    dateDeadline = "--/--/----",
    timeDeadline = "--:--",
    description = "Please create a task",
    id = 0)
)

fun List<Task>?.orPlaceHolderList(): List<Task> {
    return if (!this.isNullOrEmpty()) {
        this
    } else placeHolderList
}