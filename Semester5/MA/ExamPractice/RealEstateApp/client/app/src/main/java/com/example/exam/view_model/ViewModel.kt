package com.example.exam.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.exam.Constants.SERVER_IP
import com.example.exam.Constants.SERVER_PORT
import com.example.exam.model.Property
import com.example.exam.persistence.AppDao
import com.example.exam.server.ApiService
import com.example.exam.server.MyWebSocketClient
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


class EntityViewModel(
    private val db: AppDao,
    private val apiService: ApiService
) : ViewModel() {
    var entities: LiveData<List<Property>> = MutableLiveData(emptyList());
    private val TAG = "VIEW MODEL ::"
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
                fetchDataFromServer()
                Log.d(TAG, "Entities fetched from server")

            } else {
                Log.d(TAG, "Internet unavailable")
                fetchLocalEntities()
                Log.d(TAG, "Entities fetched from local db")
            }

        }
        Log.d(TAG, "ViewModel initialized")
    }

    suspend fun checkInternet(): Boolean {
        return withContext(Dispatchers.IO) {
            isInternetAvailable()
        }
    }

    fun refreshAction() {
        createWebSocketClient()
        fetchDataFromServer()
    }

    private fun createWebSocketClient() {
        try {
            val uri = URI("ws://${SERVER_IP}:${SERVER_PORT}/ws/socket-server/")
            webSocketClient =
                MyWebSocketClient(uri, { fetchDataFromServer() }, { updateToastMessage(it) })

            webSocketClient?.connect()
            Log.d("Create Web Socket", "WEB SOCKET CONNECTED")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            updateToastMessage("Socket error")
        }
    }

    private fun fetchLocalEntities() {
        viewModelScope.launch(Dispatchers.IO) {
            val localEntities = db.getEntities()
            withContext(Dispatchers.Main) {
                (entities as MutableLiveData).postValue(localEntities)
            }
        }
    }

    private fun fetchDataFromServer() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getEntities().execute()

                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    Log.d(TAG, "Data: $data")

                    withContext(Dispatchers.Main) {
                        (entities as MutableLiveData).postValue(data)
                    }
                    Log.d(TAG, "Response successful: ${response.body()}")

                    viewModelScope.launch(Dispatchers.IO) {
                        db.deleteAllEntities()
                        for (entity in data)
                            db.insertEntity(entity)
                        Log.d(TAG, "Local db entities updated")
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

    private suspend fun isInternetAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val socket = Socket()
                val socketAddress = InetSocketAddress(SERVER_IP, SERVER_PORT.toInt())
                socket.connect(socketAddress, 1500)
                socket.close()
                true
            } catch (e: Exception) {
                Log.e(TAG, "Is internet error: ${e.message}", e)
                updateToastMessage("Connection error")
                false
            }
        }
    }

    fun filterEntities(type: String, price: String, bedrooms: String) {
        if (type == "" && price == "" && bedrooms == "")
            fetchDataFromServer()
        else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = apiService.searchEntities().execute()

                    if (response.isSuccessful) {
                        val data = response.body() ?: emptyList()
                        Log.d(TAG, "Search: $data")
                        Log.d(TAG, "type: $type")
                        val filterResult = mutableListOf<Property>()

                        for (entity in data) {
                            if ((entity.type!!.contains(type) || type == "") &&
                                (entity.price.toString() == price || price == "") &&
                                (entity.bedrooms.toString() == bedrooms || bedrooms == "")
                            ) {
                                Log.d(TAG, "entity: $entity")
                                filterResult.add(entity)
                            }
                        }
                        Log.d(TAG, "filterResult: $filterResult")
                        val sortedList =
                            filterResult.sortedWith(compareByDescending<Property?> { it!!.date }.thenBy { it!!.price })
                        Log.d(TAG, "filterResult: $sortedList")

                        withContext(Dispatchers.Main) {
                            (entities as MutableLiveData).postValue(sortedList)
                        }
                    } else {
                        Log.d(TAG, "Response unsuccessful: ${response.code()}")
                        updateToastMessage("Filter error")
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "IOException: ${e.message}", e)
                    updateToastMessage("Filter error")
                } catch (e: Exception) {
                    Log.e(TAG, "Error fetching data: ${e.message}", e)
                    updateToastMessage("Filter error")
                }
            }
        }
    }

    fun deleteEntity(entity: Property) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.deleteEntity(entity.id).execute()

                if (response.isSuccessful) {
                    Log.d(TAG, "Delete successfully")
                    db.deleteEntity(entity)
                } else {
                    Log.d(TAG, "Delete unsuccessful: ${response.code()}")
                    updateToastMessage("Delete error")
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
                updateToastMessage("Delete error")
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting user: ${e.message}", e)
                updateToastMessage("Delete error")
            }
        }
    }

    fun updateEntity(entity: Property) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.updateEntity(entity.id, entity).execute()

                if (response.isSuccessful) {
                    val createdUser = response.body()
                    Log.d(TAG, "Update successfully: $createdUser")
                    Log.d(TAG, "Updated Response body: ${response.errorBody()?.string()}")
                    db.updateEntity(entity)
                } else {
                    Log.d(TAG, "Update unsuccessful: ${response.code()}")
                    updateToastMessage("Update error")
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
                updateToastMessage("Update error")
            } catch (e: Exception) {
                Log.e(TAG, "Error creating user: ${e.message}", e)
                updateToastMessage("Update error")
            }
        }
    }

    fun createEntity(
        date: String,
        type: String,
        address: String,
        bedrooms: Int,
        bathrooms: Int,
        price: Double,
        area: Int,
        notes: String,
    ) {
        val entity = Property(-1, date, type, address, bedrooms, bathrooms, price, area, notes)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.createEntity(entity).execute()

                if (response.isSuccessful) {
                    val createdEntity = response.body()
                    Log.d(TAG, "Created entity successfully: $createdEntity")
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

    suspend fun getEntity(id: Int): Property? {
        try {
            if (!isInternetAvailable()){
                return db.getEntityById(id)
            }
            val response = apiService.getEntity(id).execute()
            if (response.isSuccessful) {
                val entity = response.body()
                Log.d(TAG, "Retrieved successfully: $entity")
                Log.d(TAG, "Response body: ${response.errorBody()?.string()}")
                db.updateEntity(entity!!)
                return entity
            } else {
                Log.d(
                    TAG,
                    "Retrieved unsuccessful from server, details from local db: ${response.code()}"
                )
                updateToastMessage("Retrieve error")
                return db.getEntityById(id)
            }
        } catch (e: IOException) {
            Log.e(TAG, "IOException: ${e.message}", e)
            updateToastMessage("Retrieve from server error")
            return db.getEntityById(id)
        } catch (e: Exception) {
            Log.e(TAG, "Error body: ${e.message}", e)
            updateToastMessage("Retrieve from server error")
            return db.getEntityById(id)
        }
    }
}

class ViewModelFactory(
    private val db: AppDao,
    private val apiService: ApiService
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EntityViewModel(
            db = db,
            apiService = apiService
        ) as T
    }
}
