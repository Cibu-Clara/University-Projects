package com.example.realestateapp.ViewModel

import ApiService
import com.example.exam.server.MyWebSocketClient
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.model.Property
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.gusavila92.websocketclient.WebSocketClient
import java.io.IOException
import java.net.InetAddress
import java.net.URI
import java.net.URISyntaxException
import java.net.UnknownHostException
import java.util.UUID

class UsersViewModel (
    private val db : AppDao,
    private val apiService: ApiService
): ViewModel() {
    var entities: LiveData<List<Property>> = MutableLiveData(emptyList());
    private val TAG = "View Model ::"

    var isInternetConnected = MutableLiveData<Boolean>()
    private var webSocketClient: WebSocketClient? = null
    private var isSyncedToServer = false
    private var lastInternetStatus = false

    init {
        createWebSocketClient()
        startInternetCheck()
        viewModelScope.launch(Dispatchers.IO) {
            val isConnected = isInternetAvailable()
            withContext(Dispatchers.Main) {
                isInternetConnected.value = isConnected
                lastInternetStatus = isConnected
                Log.d("Internet Status", "lastInternetStatus:  $lastInternetStatus")
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                isInternetConnected.observeForever {
                    if (it) {
                        Log.d(TAG, "Internet available")
                        if (!isSyncedToServer) {
                            syncLocalDbToServer()
                            isSyncedToServer = true
                            lastInternetStatus = false
                            Log.d(TAG, "local db synced with server and users fetch from server")
                        }
                        fetchDataFromServer()
                    } else {
                        Log.d(TAG, "Internet unavailable")
                        isSyncedToServer = false
                        fetchLocalRegularUsers()
                        Log.d(TAG, "Users fetch from local db")
                    }
                }
            }
        }
        Log.d(TAG, "UsersViewModel initialized")
    }

    private fun createWebSocketClient() {
        try {
            val uri = URI("ws://10.220.27.126:8000/ws/socket-server/")
            webSocketClient = MyWebSocketClient(uri) {
                fetchDataFromServer()
            }
            webSocketClient?.connect()
            Log.d("Create Web Socket", "WEB SOCKET CONNECTED")

        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }
    private fun startInternetCheck() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(3000)
                val isConnected = isInternetAvailable()
                if(isConnected != lastInternetStatus){
                    withContext(Dispatchers.Main) {
                        isInternetConnected.value = isConnected
                        lastInternetStatus = isConnected
                        Log.d("Internet Status", "lastInternetStatus:  $isConnected")
                    }
                }
            }
        }
    }

    private fun fetchLocalRegularUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val localRegularUsers = db.getRegularUsers()
            withContext(Dispatchers.Main) {
                (entities as MutableLiveData).postValue(localRegularUsers)
            }
        }
    }

    private fun syncLocalDbToServer() {
        viewModelScope.launch(Dispatchers.IO) {
            val localUsers = db.getRegularUsers()
            for (user in localUsers) {
                try {
                    val response = apiService.createRegularUser(user).execute()

                    if (response.isSuccessful) {
                        val createdUser = response.body()
                        Log.d(TAG, "User created successfully: $createdUser")
                        Log.d(TAG, "Response body: ${response.errorBody()?.string()}")
                    } else {
                        Log.d(TAG, "User creation unsuccessful: ${response.errorBody()?.string() + response.code()}")
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "IOException: ${e.message}", e)
                } catch (e: Exception) {
                    Log.e(TAG, "Error creating user: ${e.message}", e)
                }
            }
        }
    }

    private fun fetchDataFromServer() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getRegularUsers().execute()

                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    withContext(Dispatchers.Main) {
                        (entities as MutableLiveData).postValue(data)
                    }
                    Log.d(TAG, "Response successful")
                    viewModelScope.launch(Dispatchers.IO) {
                        delay(3000)
                        db.deleteAllRegularUsers()
                        for (user in data)
                            db.insertRegularUser(user)
                        Log.d(TAG, "Local db users updated")
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

    fun deleteRegularUser(user: Property) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.deleteRegularUser(user.uuid).execute()

                if (response.isSuccessful) {
                    Log.d(TAG, "User deleted successfully")
                    db.deleteRegularUser(user)
                } else {
                    Log.d(TAG, "User deletion unsuccessful: ${response.code()}")
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting user: ${e.message}", e)
            }
        }
    }

    fun updateRegularUser(user: RegularUser) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.updateRegularUser(user.uuid, user).execute()

                if (response.isSuccessful) {
                    val createdUser = response.body()
                    Log.d(TAG, "User updated successfully: $createdUser")
                    Log.d(TAG, "updated Response body: ${response.errorBody()?.string()}")
                    db.updateRegularUser(user)
                } else {
                    Log.d(TAG, "User update unsuccessful: ${response.code()}")
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
            } catch (e: Exception) {
                Log.e(TAG, "Error creating user: ${e.message}", e)
            }
        }
    }

    fun createRegularUser(
        username: String,
        first_name: String,
        last_name: String,
        email: String,
        password: String
    ) {
        val user = RegularUser(
            uuid = UUID.randomUUID().toString(),
            username = username,
            first_name = first_name,
            last_name = last_name,
            email = email,
            password = password
        );
        if(isInternetConnected.value == true){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = apiService.createRegularUser(user).execute()

                    if (response.isSuccessful) {
                        val createdUser = response.body()
                        Log.d(TAG, "User created successfully: $createdUser")
                        Log.d(TAG, "Response body: ${response.errorBody()?.string()}")
                    } else {
                        Log.d(TAG, "User creation unsuccessful: ${response.code()}")
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "IOException: ${e.message}", e)
                } catch (e: Exception) {
                    Log.e(TAG, "Error creating user: ${e.message}", e)
                }
            }
        }else{
            viewModelScope.launch(Dispatchers.IO){
                db.insertRegularUser(user)
                fetchLocalRegularUsers()
            }
        }

    }

    fun getRegularUser(userUUID: UUID): RegularUser? {
        try {
            val response = apiService.getRegularUser1(userUUID.toString()).execute()

            if (response.isSuccessful) {
                val user = response.body()
                Log.d(TAG, "User retrieved successfully: $user")
                Log.d(TAG, "Response body: ${response.errorBody()?.string()}")
                return user
            } else {
                Log.d(TAG, "User retrieved unsuccessful: ${response.code()}")
            }
        } catch (e: IOException) {
            Log.e(TAG, "IOException: ${e.message}", e)
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieved user: ${e.message}", e)
        }
        return null
    }
}
class ViewModelFactory (
    private val db: AppDao,
    private val apiService: ApiService
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsersViewModel(
            db = db,
            apiService = apiService
        ) as T
    }
}
