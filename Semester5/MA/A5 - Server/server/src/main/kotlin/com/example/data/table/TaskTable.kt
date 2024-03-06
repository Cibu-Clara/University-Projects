package com.example.data.table

import org.jetbrains.exposed.sql.Table

object TaskTable : Table() {
    val id = integer("id").autoIncrement()
    val title = text("title")
    val priorityLevel = text("priorityLevel")
    val dateDeadline = text("dateDeadline")
    val timeDeadline = text("timeDeadline")
    val description = text("description")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}