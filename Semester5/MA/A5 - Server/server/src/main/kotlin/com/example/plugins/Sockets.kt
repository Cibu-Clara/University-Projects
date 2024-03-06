package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.time.Duration
import java.util.Collections
import java.util.LinkedHashSet

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(10)
        timeout = Duration.ofSeconds(99)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val taskConsumer = TaskConsumer()

    routing {
        webSocket("/ws/socket-server") {
            clients.add(this) // Add the WebSocket session to the clients set
            taskConsumer.connect()

            try {
                incoming.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        taskConsumer.onReceive(text)
                    }
                }
            } finally {
                taskConsumer.disconnect()
                clients.remove(this) // Remove the WebSocket session from the clients set upon disconnection
            }
        }
    }
}

val clients: MutableSet<WebSocketSession> = Collections.synchronizedSet(LinkedHashSet())

suspend fun WebSocketSession.sendTasksChanged(message: String) {
    send(Frame.Text(message))
}

class TaskConsumer {

    fun connect() {
        // Handle connection logic
        println("Connected")
    }

    fun disconnect() {
        // Handle disconnection logic
        println("Disconnected")
    }

    fun onReceive(text: String) {
        println("Received: $text")
    }

    suspend fun tasksChanged(text: String) {
        println("Sending: tasks changed")

        clients.forEach {
            it.sendTasksChanged(
                """
    {
        type: "tasks_changed",
        message: "$text"
    }
    """
            )

        }
    }
}