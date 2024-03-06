package com.example.data.model

data class Task(
    val id: Int,
    val title: String,
    val priorityLevel: String,
    val dateDeadline: String,
    val timeDeadline: String,
    val description: String
)
