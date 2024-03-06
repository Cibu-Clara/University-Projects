package com.example.taskmanagement.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task (
    var title: String,
    var priorityLevel: String,
    var dateDeadline: String,
    var timeDeadline: String,
    var description: String,
    var id:Int
) : Parcelable {

    companion object {
        var currentId = 0
    }

    constructor(
        title: String,
        priorityLevel: String = "None",
        dateDeadline: String = "--/--/----",
        timeDeadline: String = "--:--",
        description: String = "No description."
    ) : this(title,
        priorityLevel = priorityLevel.ifBlank { "None" },
        dateDeadline = dateDeadline.ifBlank { "--/--/----" },
        timeDeadline = timeDeadline.ifBlank { "--:--" },
        description = description.ifBlank { "No description." },
        currentId++)
}