package com.example.plugins

import com.example.repository.TaskRepository
import com.example.routes.taskRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val db = TaskRepository()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        taskRoutes(db)
    }
}
