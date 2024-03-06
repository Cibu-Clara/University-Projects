package com.example.taskapp.utils

import android.util.Log
import org.json.JSONObject
import tech.gusavila92.websocketclient.WebSocketClient
import java.net.URI

class MyWebSocketClient(uri: URI, private val onTaskChanged: () -> Unit) : WebSocketClient(uri) {
    override fun onOpen() {
        Log.i("WebSocket", "Session is starting")
    }

    override fun onTextReceived(s: String) {
        Log.i("WebSocket", "Message received: $s")
        val messageJson = JSONObject(s.trimIndent())
        if (messageJson.optString("type") == "tasks_changed") {
            Log.i("WebSocket", "Tasks changed")
            onTaskChanged.invoke()
        }
    }

    override fun onBinaryReceived(data: ByteArray) {
        Log.i("WebSocket", "binary")
    }

    override fun onPingReceived(data: ByteArray) {
        Log.i("WebSocket", "ping")
    }

    override fun onPongReceived(data: ByteArray) {
        Log.i("WebSocket", "pong")
    }

    override fun onException(e: Exception) {
        Log.e("WebSocket", e.message ?: "Exception occurred")
    }

    override fun onCloseReceived() {
        Log.i("WebSocket", "Closed ")
        println("onCloseReceived")
    }
}
