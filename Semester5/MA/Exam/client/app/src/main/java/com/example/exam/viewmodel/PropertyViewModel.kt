package com.example.exam.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.exam.Constants
import com.example.exam.model.Property
import com.example.exam.persistence.PropertyDao
import com.example.exam.server.MyWebSocketClient
import com.example.exam.server.PropertyAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.gusavila92.websocketclient.WebSocketClient
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URI
import java.net.URISyntaxException

class PropertyViewModel(
    private val db: PropertyDao,
    private val propertyAPI: PropertyAPI
) : ViewModel() {
    var properties: LiveData<List<Property>> = MutableLiveData(emptyList())
    var types: LiveData<List<String>> = MutableLiveData(emptyList())
    private val TAG = "VIEW MODEL"
    private var webSocketClient: WebSocketClient? = null

    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> get() = _toastMessage

    fun updateToastMessage(message: String) {
        _toastMessage.value = message
        Log.d("TOAST UPDATE", "toast update with message:${message}")
    }

    fun clearToastMessage() {
        _toastMessage.value = ""
    }

    init {
        createWebSocketClient()

        viewModelScope.launch(Dispatchers.IO) {
            val isConnected = isInternetAvailable()
            if (isConnected) {
                Log.d(TAG, "Internet available")
                fetchPropertiesFromServer()
                Log.d(TAG, "Entities fetched from server")

            } else {
                Log.d(TAG, "Internet unavailable")
                fetchLocalProperties()
                Log.d(TAG, "Entities fetched from local db")
            }

        }
        Log.d(TAG, "ViewModel initialized")
    }

    private fun createWebSocketClient() {
        try {
            val uri = URI("ws://${Constants.SERVER_IP}:${Constants.SERVER_PORT}/ws/socket-server/")
            webSocketClient =
                MyWebSocketClient(uri, { fetchPropertiesFromServer() }, { updateToastMessage(it) })

            webSocketClient?.connect()
            Log.d("Create Web Socket", "WEB SOCKET CONNECTED")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            updateToastMessage("Socket error")
        }
    }

    private suspend fun isInternetAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val socket = Socket()
                val socketAddress = InetSocketAddress(Constants.SERVER_IP, Constants.SERVER_PORT.toInt())
                socket.connect(socketAddress, 1500)
                socket.close()
                true
            } catch (e: Exception) {
                Log.e(TAG, "Internet error: ${e.message}", e)
                updateToastMessage("Connection error")
                false
            }
        }
    }

    suspend fun checkInternet(): Boolean {
        return withContext(Dispatchers.IO) {
            isInternetAvailable()
        }
    }

    fun refreshAction() {
        createWebSocketClient()
        fetchPropertiesFromServer()
    }

    private fun fetchLocalTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            val localDates = db.getTypes()
            withContext(Dispatchers.Main) {
                (types as MutableLiveData).postValue(localDates)
            }
        }
    }

    fun fetchTypesFromServer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = propertyAPI.getTypes().execute()
                Log.d(TAG, "Data: ${response.body()}")

                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    Log.d(TAG, "Data: $data")

                    withContext(Dispatchers.Main) {
                        (types as MutableLiveData).postValue(data)
                    }
                    Log.d(TAG, "Response successful: ${response.body()}")
                } else {
                    Log.d(TAG, "Response unsuccessful: ${response.code()}")
                    updateToastMessage("Server error")
                    fetchLocalTypes()
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
                updateToastMessage("Server error")
                fetchLocalTypes()

            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data: ${e.message}", e)
                updateToastMessage("Server error")
                fetchLocalTypes()
            }
        }
    }


    private fun fetchPropertiesFromServer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = propertyAPI.getProperties().execute()

                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    Log.d(TAG, "Data: $data")

                    withContext(Dispatchers.Main) {
                        (properties as MutableLiveData).postValue(data)
                    }
                    Log.d(TAG, "Response successful: ${response.body()}")

                    viewModelScope.launch(Dispatchers.IO) {
                        db.deleteAllProperties()
                        for (entity in data) {
                            db.insertProperty(entity)
                        }
                        Log.d(TAG, "Properties from local db updated")
                    }
                } else {
                    Log.d(TAG, "Response unsuccessful: ${response.code()}")
                    updateToastMessage("Server error")
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
                updateToastMessage("Server error")
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data: ${e.message}", e)
                updateToastMessage("Server error")
            }
        }
    }

    private fun fetchLocalProperties() {
        viewModelScope.launch(Dispatchers.IO) {
            val localProperties = db.getProperties()
            withContext(Dispatchers.Main) {
                (properties as MutableLiveData).postValue(localProperties)
            }
        }
    }

    suspend fun getProperty(id: Int): Property? {
        try {
            if (!isInternetAvailable()) {
                return db.getPropertyById(id)
            }
            val response = propertyAPI.getPropertyById(id).execute()

            return if (response.isSuccessful) {
                val property = response.body()
                Log.d(TAG, "Property retrieved successfully: $property")
                Log.d(TAG, "Response body: ${response.errorBody()?.string()}")
                db.updateProperty(property!!)
                property
            } else {
                Log.d(TAG,"Property retrieved unsuccessful from server, details from local db: ${response.code()}")
                updateToastMessage("Retrieve error")
                db.getPropertyById(id)
            }
        } catch (e: IOException) {
            Log.e(TAG, "IOException: ${e.message}", e)
            updateToastMessage("Error retrieving from server")
            return db.getPropertyById(id)
        } catch (e: Exception) {
            Log.e(TAG, "Error body: ${e.message}", e)
            updateToastMessage("Error retrieving from server")
            return db.getPropertyById(id)
        }
    }

    fun createProperty(
        name: String,
        date: String,
        details: String,
        status: String,
        viewers: Int,
        type: String
    ) {
        val property = Property(-1, name, date, details, status, viewers, type)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = propertyAPI.createProperty(property).execute()

                if (response.isSuccessful) {
                    val createdProperty = response.body()
                    Log.d(TAG, "Property created successfully: $createdProperty")
                    Log.d(TAG, "Response body: ${response.errorBody()?.string()}")
                } else {
                    Log.d(TAG, "Creation unsuccessful: ${response.code()}")
                    updateToastMessage("Create error")
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
                updateToastMessage("Create error")
            } catch (e: Exception) {
                Log.e(TAG, "Error creating: ${e.message}", e)
                updateToastMessage("Create error")
            }
        }
    }
}

class ViewModelFactory(
    private val db: PropertyDao,
    private val propertyAPI: PropertyAPI
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PropertyViewModel(
            db = db,
            propertyAPI = propertyAPI
        ) as T
    }
}