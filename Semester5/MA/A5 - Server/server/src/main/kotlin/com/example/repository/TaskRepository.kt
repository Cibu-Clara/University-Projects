package com.example.repository

import com.example.data.model.Task
import com.example.data.table.TaskTable
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class TaskRepository {

    suspend fun getAllTasks() : List<Task> = dbQuery {
        TaskTable
            .selectAll()
            .mapNotNull {
                rowToTask(it)
            }
    }

    suspend fun getTaskById(id: Int): Task? = dbQuery {
        TaskTable
            .select { TaskTable.id eq id }
            .mapNotNull {
                rowToTask(it)
            }
            .singleOrNull()
    }

    suspend fun doesTaskExist(task: Task): Boolean = dbQuery {
        TaskTable
            .select { TaskTable.id eq task.id }
            .empty()
    }

    suspend fun addTask(task: Task) {
        dbQuery {
            TaskTable.insert { tt ->
                tt[title] = task.title
                tt[priorityLevel] = task.priorityLevel
                tt[dateDeadline] = task.dateDeadline
                tt[timeDeadline] = task.timeDeadline
                tt[description] = task.description
            }
        }
    }

    suspend fun updateTask(task: Task) {
        dbQuery {
            TaskTable.update(
                where = {
                    TaskTable.id.eq(task.id)
                }
            ) { tt ->
                tt[title] = task.title
                tt[priorityLevel] = task.priorityLevel
                tt[dateDeadline] = task.dateDeadline
                tt[timeDeadline] = task.timeDeadline
                tt[description] = task.description
            }
        }
    }

    suspend fun deleteTask(id: Int) {
        dbQuery {
            TaskTable.deleteWhere {
                TaskTable.id.eq(id)
            }
        }
    }

    private fun rowToTask(row: ResultRow?) : Task? {
        if (row == null) {
            return null
        }

        return Task(
            id = row[TaskTable.id],
            title = row[TaskTable.title],
            priorityLevel = row[TaskTable.priorityLevel],
            dateDeadline = row[TaskTable.dateDeadline],
            timeDeadline = row[TaskTable.timeDeadline],
            description = row[TaskTable.description]
        )
    }
}