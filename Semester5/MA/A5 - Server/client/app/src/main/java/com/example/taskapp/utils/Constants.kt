package com.example.taskapp.utils

import com.example.taskapp.data.model.Task

object Constants {

    const val TABLE_NAME = "tasks"
    const val DATABASE_NAME = "tasks_db"

    const val NAVIGATION_TASK_ADD = "taskAdd"
    const val NAVIGATION_TASKS_LIST = "tasksList"
    const val NAVIGATION_TASK_DETAILS = "taskDetails/{taskId}"
    const val NAVIGATION_TASK_EDIT = "taskEdit/{taskId}"
    const val NAVIGATION_TASK_ID_ARGUMENT = "taskId"

    val taskDetailsPlaceholder = Task(
        title = "Cannot find task details",
        priorityLevel = "None",
        dateDeadline = "--/--/----",
        timeDeadline = "--:--",
        description = "None",
        id = 0
    )

    fun taskDetailsNavigation(taskId : Int) = "taskDetails/$taskId"
    fun taskEditNavigation(taskId : Int) = "taskEdit/$taskId"
}