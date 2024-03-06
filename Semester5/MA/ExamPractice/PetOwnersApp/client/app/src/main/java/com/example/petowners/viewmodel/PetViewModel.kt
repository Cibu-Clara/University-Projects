package com.example.petowners.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.petowners.Constants.SERVER_IP
import com.example.petowners.Constants.SERVER_PORT
import com.example.petowners.model.Pet
import com.example.petowners.persistence.AppDao
import com.example.petowners.server.ApiService
import com.example.petowners.server.MyWebSocketClient
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

class PetViewModel(
    private val db: AppDao,
    private val apiService: ApiService
) : ViewModel() {
    var pets: LiveData<List<Pet>> = MutableLiveData(emptyList())
    private val TAG = "VIEW MODEL"
    private var webSocketClient: WebSocketClient? = null

    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> get() = _toastMessage

    fun updateToastMessage(message: String) {
        _toastMessage.value = message
        Log.d("TOAST UPDATE", "Toast update with message:${message}")
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
                Log.d(TAG, "Data fetched from server")
            } else {
                Log.d(TAG, "Internet unavailable")
                fetchLocalData()
                Log.d(TAG, "Data fetched from local db")
            }
        }
        Log.d(TAG, "ViewModel initialized")
    }

    private fun createWebSocketClient() {
        try {
            val uri = URI("ws://${SERVER_IP}:${SERVER_PORT}/ws/socket-server")
            webSocketClient = MyWebSocketClient(uri, { fetchDataFromServer() }, {updateToastMessage(it) })

            webSocketClient?.connect()
            Log.d("WebSocket", "WEB SOCKET CONNECTED")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            updateToastMessage("Socket error")
        }
    }

    fun refreshAction() {
        createWebSocketClient()
        fetchDataFromServer()
    }

    suspend fun checkInternet(): Boolean {
        return withContext(Dispatchers.IO) {
            isInternetAvailable()
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
                Log.e(TAG, "Internet error: ${e.message}", e)
                updateToastMessage("Connection error")
                false
            }
        }
    }

    private fun fetchDataFromServer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getPets().execute()

                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    Log.d(TAG, "Data: $data")

                    withContext(Dispatchers.Main) {
                        (pets as MutableLiveData).postValue(data)
                    }
                    Log.d(TAG, "Response successful: ${response.body()}")

                    viewModelScope.launch(Dispatchers.IO) {
                        db.deleteAllPets()
                        for (entity in data) {
                            db.insertPet(entity)
                        }
                        Log.d(TAG, "Pets from local db updated")
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

    private fun fetchLocalData() {
        viewModelScope.launch(Dispatchers.IO) {
            val localPets = db.getPets()
            withContext(Dispatchers.Main) {
                (pets as MutableLiveData).postValue(localPets)
            }
        }
    }

    suspend fun getPet(id: Int): Pet? {
        try {
            if (!isInternetAvailable()) {
                return db.getPetById(id)
            }
            val response = apiService.getPetById(id).execute()

            return if (response.isSuccessful) {
                val pet = response.body()
                Log.d(TAG, "Pet retrieved successfully: $pet")
                Log.d(TAG, "Response body: ${response.errorBody()?.string()}")
                db.updatePet(pet!!)
                pet
            } else {
                Log.d(TAG,"Pet retrieved unsuccessful from server, details from local db: ${response.code()}")
                updateToastMessage("Retrieve error")
                db.getPetById(id)
            }
        } catch (e: IOException) {
            Log.e(TAG, "IOException: ${e.message}", e)
            updateToastMessage("Error retrieving from server")
            return db.getPetById(id)
        } catch (e: Exception) {
            Log.e(TAG, "Error body: ${e.message}", e)
            updateToastMessage("Error retrieving from server")
            return db.getPetById(id)
        }
    }

    fun createPet(
        name: String,
        breed: String,
        age: Int,
        weight: Int,
        owner: String,
        location: String,
        description: String
    ) {
        val pet = Pet(-1, name, breed, age, weight, owner, location, description)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.createPet(pet).execute()

                if (response.isSuccessful) {
                    val createdPet = response.body()
                    Log.d(TAG, "Pet created successfully: $createdPet")
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

    fun deletePet(pet: Pet) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.deletePet(pet.id).execute()

                if (response.isSuccessful) {
                    Log.d(TAG, "Pet deleted successfully")
                    db.deletePet(pet)
                } else {
                    Log.d(TAG, "Delete unsuccessful: ${response.code()}")
                    updateToastMessage("Delete error")
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
                updateToastMessage("Delete error")
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting pet: ${e.message}", e)
                updateToastMessage("Delete error")
            }
        }
    }

    fun filterPets(breed: String, age: String, location: String) {
        if (breed == "" && age == "" && location == "") {
            fetchDataFromServer()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = apiService.searchPets().execute()

                    if (response.isSuccessful) {
                        val data = response.body() ?: emptyList()
                        Log.d(TAG, "Search: $data")
                        val filterResult = mutableListOf<Pet>()

                        for(entity in data) {
                            if ((entity.breed!!.contains(breed) || breed == "") &&
                                (entity.age.toString() == age || age == "") &&
                                (entity.location!!.contains(location) || location == "")
                                ) {
                                Log.d(TAG, "Pet: $entity")
                                filterResult.add(entity)
                            }
                        }
                        Log.d(TAG, "Filter Result: $filterResult")
                        val sortedList = filterResult.sortedWith(compareByDescending<Pet?> { it!!.weight }.thenBy { it!!.age })
                        Log.d(TAG, "Filter Result: $sortedList")

                        withContext(Dispatchers.Main) {
                            (pets as MutableLiveData).postValue(sortedList)
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
}

class ViewModelFactory(
    private val db: AppDao,
    private val apiService: ApiService
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PetViewModel(
            db = db,
            apiService = apiService
        ) as T
    }
}