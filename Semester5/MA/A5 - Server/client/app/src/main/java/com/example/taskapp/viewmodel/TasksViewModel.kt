package com.example.taskapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskapp.data.TaskAPI
import com.example.taskapp.data.model.Task
import com.example.taskapp.data.persistence.TasksDao
import com.example.taskapp.utils.MyWebSocketClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.gusavila92.websocketclient.WebSocketClient
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URI
import java.net.URISyntaxException
import java.net.UnknownHostException

class TasksViewModel(private val db: TasksDao, private val taskAPI: TaskAPI) : ViewModel() {

    var tasks: LiveData<List<Task>> = MutableLiveData(emptyList())
    private val TAG = "TasksViewModel"
    var isConnected = MutableLiveData<Boolean>()
    private var webSocketClient: WebSocketClient? = null
    private var isSyncedToServer = false
    private var lastInternetStatus = false
    private var lastServerStatus = false

    init {
        startInternetCheck()

        viewModelScope.launch(Dispatchers.IO) {
            val isInternetConnected = isInternetAvailable()
            val isServerReachable = isServerReachable()
            withContext(Dispatchers.Main) {
                isConnected.value = isInternetConnected && isServerReachable
                lastInternetStatus = isInternetConnected
                lastServerStatus = isServerReachable
                Log.d("Internet Status", "lastInternetStatus:  $lastInternetStatus")
                Log.d("Server Status", "lastServerStatus:  $lastServerStatus")
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                isConnected.observeForever { isConnected ->
                    if (isConnected) {
                        Log.d(TAG, "Connected to the server")
                        try {
                            createWebSocketClient()
                            if (!isSyncedToServer) {
                                syncLocalDbToServer()
                                isSyncedToServer = true
                                Log.d(TAG, "Local db synced to server")
                            }
                            fetchDataFromServer()
                        } catch (e: Exception) {
                            Log.e(TAG, "Error creating WebSocket client: ${e.message}", e)
                        }
                    } else {
                        Log.d(TAG, "Failed to connect to the server")
                        isSyncedToServer = false
                        fetchLocalTasks()
                        Log.d(TAG, "Tasks fetched from local db")
                    }
                }

            }
        }
        Log.d(TAG, "TasksViewModel initialized")
    }

    private fun startInternetCheck() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(3000)
                val isInternetConnected = isInternetAvailable()
                val isServerReachable = isServerReachable()
                if (isInternetConnected != lastInternetStatus || isServerReachable != lastServerStatus) {
                    withContext(Dispatchers.Main) {
                        isConnected.value = isInternetConnected && isServerReachable
                        lastInternetStatus = isInternetConnected
                        lastServerStatus = isServerReachable
                        Log.d("Internet Status", "lastInternetStatus:  $lastInternetStatus")
                        Log.d("Server Status", "lastServerStatus:  $lastServerStatus")
                    }
                }
            }
        }
    }

    private fun createWebSocketClient() {
        try {
            val uri = URI("ws://172.30.113.46:8080/ws/socket-server")
            webSocketClient = MyWebSocketClient(uri) {
                fetchDataFromServer()
            }
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    webSocketClient?.connect()
                    Log.d(TAG, "Connected to web socket")
                } catch (e: Exception) {
                    Log.e(TAG, "WebSocket connection failed: ${e.message}", e)
                }
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    private fun fetchLocalTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val localTasks = db.getTasks()
            withContext(Dispatchers.Main) {
                (tasks as MutableLiveData).postValue(localTasks)
            }
        }
    }

    private fun syncLocalDbToServer() {
        viewModelScope.launch(Dispatchers.IO) {
            val localTasks = db.getTasks()
            for (task in localTasks) {
                try {
                    val response = taskAPI.createTask(task).execute()

                    if (response.isSuccessful) {
                        val createdTask = response.body()
                        Log.d(TAG, "Task created successfully: $createdTask")
                        Log.d(TAG, "Response body: ${response.errorBody()?.string()}")
                    } else {
                        Log.d(
                            TAG,
                            "Task creation unsuccessful: ${
                                response.errorBody()?.string() + response.code()
                            }"
                        )
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "IOException: ${e.message}", e)
                } catch (e: Exception) {
                    Log.e(TAG, "Error creating task: ${e.message}", e)
                }
            }
        }
    }

    private fun fetchDataFromServer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = taskAPI.getTasks().execute()

                if (response.isSuccessful) {
                    val serverTasks = response.body() ?: emptyList()
                    val localTasks = db.getTasks()

                    // Identify new, updated, and deleted tasks
                    val newTasks =
                        serverTasks.filterNot { serverTask -> localTasks.any { it.id == serverTask.id } }
                    val updatedTasks =
                        serverTasks.filter { serverTask -> localTasks.any { it.id == serverTask.id && it != serverTask } }
                    val deletedTasks =
                        localTasks.filterNot { localTask -> serverTasks.any { it.id == localTask.id } }

                    // Update LiveData with server data
                    withContext(Dispatchers.Main) {
                        (tasks as MutableLiveData).postValue(serverTasks)
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        db.insertTasks(*newTasks.toTypedArray()) // Insert new tasks
                        db.updateTasks(*updatedTasks.toTypedArray()) // Update existing tasks
                        db.deleteTasks(*deletedTasks.toTypedArray()) // Delete tasks not present on the server
                        Log.d(TAG, "Tasks fetched from server")
                    }
                } else {
                    Log.d(TAG, "Response unsuccessful: ${response.code()}")
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data: ${e.message}", e)
            }
        }
    }

    private suspend fun isInternetAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val address = InetAddress.getByName("www.google.com")
                address.toString() != ""
            } catch (e: UnknownHostException) {
                false
            }
        }
    }

    private suspend fun isServerReachable(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val address = InetAddress.getByName("192.168.1.131")
                address.toString() != ""
            } catch (e: UnknownHostException) {
                false
            }
        }
    }

    fun createTask(
        title: String,
        priorityLevel: String,
        dateDeadline: String,
        timeDeadline: String,
        description: String
    ) {
        val task = Task(title, priorityLevel, dateDeadline, timeDeadline, description)
        if (isConnected.value == true) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = taskAPI.createTask(task).execute()
                    if (response.isSuccessful) {
                        val createdTask = response.body()
                        Log.d(TAG, "Task created successfully: $createdTask")
                        Log.d(TAG, "Response body: ${response.errorBody()?.string()}")
                    } else {
                        Log.d(TAG, "Task creation unsuccessful: ${response.code()}")
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "IOException: ${e.message}", e)
                } catch (e: Exception) {
                    Log.e(TAG, "Error creating task: ${e.message}", e)
                }
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    db.insertTask(task)
                    fetchLocalTasks()
                    Log.d(TAG, "Task added to local db")
                } catch (e: IOException) {
                    Log.e(TAG, "IOException: ${e.message}", e)
                } catch (e: Exception) {
                    Log.e(TAG, "Error adding task to local db: ${e.message}", e)
                }
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = task.id?.let { taskAPI.deleteTask(it).execute() }

                if (response != null) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "Task deleted successfully")
                        db.deleteTask(task)
                    } else {
                        Log.d(TAG, "Task deletion unsuccessful: ${response.code()}")
                    }
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting task: ${e.message}", e)
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = taskAPI.updateTask(task).execute()

                if (response.isSuccessful) {
                    val createdTask = response.body()
                    Log.d(TAG, "Task updated successfully: $createdTask")
                    db.updateTask(task)
                    Log.d(TAG, "Updated response body: ${response.errorBody()?.string()}")
                } else {
                    Log.d(TAG, "Task update unsuccessful: ${response.code()}")
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
            } catch (e: Exception) {
                Log.e(TAG, "Error updating task: ${e.message}", e)
            }
        }
    }

    fun getTask(id: Int): Task? {
        if (isConnected.value == true) {
            try {
                val response = taskAPI.getTask(id).execute()

                if (response.isSuccessful) {
                    val task = response.body()
                    Log.d(TAG, "Task retrieved successfully: $task")
                    Log.d(TAG, "Response body: ${response.errorBody()?.string()}")
                    return task
                } else {
                    Log.d(TAG, "Task retrieve unsuccessful: ${response.code()}")
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving task: ${e.message}", e)
            }
            return null
        } else {
            try {
                return db.getTaskById(id)
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving task from local db ${e.message}", e)
            }
            return null
        }
    }
}

class TaskViewModelFactory(private val db: TasksDao, private val taskAPI: TaskAPI) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        return TasksViewModel(
            db = db,
            taskAPI = taskAPI
        ) as T
    }
}