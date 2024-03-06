package com.example

import com.example.plugins.*
import com.example.repository.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun ApplicationCall.logInfo(message: String) {
    application.environment.log.info(message)
}

fun Application.module() {
    DatabaseFactory.init()

    configureSockets()
    configureSerialization()
    configureRouting()
}
