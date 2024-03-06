package com.example.routes

import com.example.data.model.SimpleResponse
import com.example.data.model.Task
import com.example.logInfo
import com.example.repository.TaskRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.websocket.*
import com.example.plugins.TaskConsumer

fun Route.taskRoutes(
    db: TaskRepository,
) {
    val taskConsumer = TaskConsumer()

    get("/tasks") {
        try {
            call.logInfo("Fetching all tasks")
            val tasks = db.getAllTasks()
            call.respond(HttpStatusCode.OK, tasks)
        } catch(e: Exception) {
            call.logInfo("Error fetching task: ${e.message}")
            call.respond(HttpStatusCode.Conflict, emptyList<Task>())
        }
    }

    get("/tasks/details") {
        val taskId = try {
            call.logInfo("Fetching task details")
            call.request.queryParameters["id"]!!.toInt()
        } catch (e: NumberFormatException) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Invalid id format. It should be an integer"))
            return@get
        } catch (e: NoSuchElementException) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Query Parameter: id is not present"))
            return@get
        }

        try {
            val task = db.getTaskById(taskId)
            if (task != null) {
                call.respond(HttpStatusCode.OK, task)
            } else {
                call.respond(HttpStatusCode.NotFound, SimpleResponse(false, "Task with id $taskId not found"))
            }
        } catch(e: Exception) {
            call.logInfo("Error fetching task details: ${e.message}")
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some problem occurred"))
        }
    }

    post("/tasks/create") {
        val task = try {
            call.logInfo("Creating a new task")
            call.receive<Task>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing fields"))
            return@post
        }

        try {
            if (!db.doesTaskExist(task)) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Duplicate key"))
            } else {
                db.addTask(task)
                taskConsumer.tasksChanged("Tasks changed")
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Task added successfully!"))
            }
        } catch (e: Exception) {
            call.logInfo("Error creating task: ${e.message}")
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some problem occurred"))
        }
    }

    put("tasks/update") {
        val task = try {
            call.logInfo("Updating a task")
            call.receive<Task>()
        }
        catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing fields"))
            return@put
        }

        try {
            db.updateTask(task)
            taskConsumer.tasksChanged("Tasks changed")
            call.respond(HttpStatusCode.OK, SimpleResponse(true, "Task updated successfully!"))
        } catch (e: Exception) {
            call.logInfo("Error updating task: ${e.message}")
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some problem occurred"))
        }
    }

    delete("tasks/delete") {
        val taskId = try {
            call.logInfo("Deleting a task")
            call.request.queryParameters["id"]!!.toInt()
        } catch (e: NumberFormatException) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Invalid id format. It should be an integer"))
            return@delete
        } catch (e: NoSuchElementException) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Query Parameter: id is not present"))
            return@delete
        }

        try {
            db.deleteTask(taskId)
            taskConsumer.tasksChanged("Tasks changed")
            call.respond(HttpStatusCode.OK, SimpleResponse(true, "Task deleted successfully!"))
        } catch (e: Exception) {
            call.logInfo("Error deleting task: ${e.message}")
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some problem occurred"))
        }
    }
}