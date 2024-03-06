package com.example.exam.server

import android.util.Log
import com.example.exam.model.Property
import com.google.gson.Gson
import tech.gusavila92.websocketclient.WebSocketClient
import java.net.URI

class MyWebSocketClient(uri: URI, private val onServerChanges: () -> Unit, private val toastMessage: (str:String) -> Unit) : WebSocketClient(uri) {
    override fun onOpen() {
        Log.i("WebSocket", "Session is starting")
    }

    override fun onTextReceived(receivedMessage: String) {
        Log.i("WebSocket", "Message received: $receivedMessage")
        val gson = Gson()
        val entity = gson.fromJson(receivedMessage, Property::class.java)

        onServerChanges.invoke()
        toastMessage.invoke(entity.toString())
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
