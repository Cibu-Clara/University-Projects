package com.example.financetracker.viewmodel

import android.util.Log
import kotlin.collections.groupBy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financetracker.Constants.SERVER_IP
import com.example.financetracker.Constants.SERVER_PORT
import com.example.financetracker.model.Transaction
import com.example.financetracker.persistence.TransactionDao
import com.example.financetracker.server.MyWebSocketClient
import com.example.financetracker.server.TransactionAPI
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

class TransactionViewModel(
    private val db: TransactionDao,
    private val transactionAPI: TransactionAPI
) : ViewModel() {

    var transactions: LiveData<List<Transaction>> = MutableLiveData(emptyList())
    var dates: LiveData<List<String>> = MutableLiveData(emptyList())

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
                fetchDatesFromServer()
                fetchAllTransactionsFromServer()
                Log.d(TAG, "Transactions fetched from server")

            } else {
                Log.d(TAG, "Internet unavailable")
                fetchLocalDates()
                Log.d(TAG, "Transactions fetched from local db")
            }
        }
        Log.d(TAG, "ViewModel initialized")
    }

    private fun createWebSocketClient() {
        try {
            val uri = URI("ws://${SERVER_IP}:${SERVER_PORT}/ws/socket-server/")
            webSocketClient =
                MyWebSocketClient(uri, { fetchDatesFromServer()}, { fetchAllTransactionsFromServer() }, {fetchDateDetailsFromServer(it)}, { updateToastMessage(it) })
            webSocketClient?.connect()
            Log.d("Web Socket", "WEB SOCKET CONNECTED")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            updateToastMessage("Socket error")
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

    suspend fun checkInternet(): Boolean {
        return withContext(Dispatchers.IO) {
            isInternetAvailable()
        }
    }

    fun refreshAction() {
        createWebSocketClient()
        fetchDatesFromServer()
        fetchAllTransactionsFromServer()
    }

    private fun fetchLocalDates() {
        viewModelScope.launch(Dispatchers.IO) {
            val localDates = db.getDates()
            withContext(Dispatchers.Main) {
                (dates as MutableLiveData).postValue(localDates)
            }
        }
    }

    private fun fetchLocalDateDetails(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val localDateDetails = db.getTransactionsByDate(date)
            withContext(Dispatchers.Main) {
                (transactions as MutableLiveData).postValue(localDateDetails)
            }
        }
    }

    private fun fetchAllLocalTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            val localTransactions = db.getTransactions()
            withContext(Dispatchers.Main) {
                (transactions as MutableLiveData).postValue(localTransactions)
            }
        }
    }

    private fun fetchAllTransactionsFromServer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = transactionAPI.getEntries().execute()

                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    Log.d(TAG, "Data: $data")

                    withContext(Dispatchers.Main) {
                        (transactions as MutableLiveData).postValue(data)
                    }
                    Log.d(TAG, "Response successful: ${response.body()}")

                    viewModelScope.launch(Dispatchers.IO) {
                        db.deleteAllTransactions()
                        for (entity in data)
                            db.insertTransaction(entity)
                        Log.d(TAG, "Local db transactions updated")
                    }
                } else {
                    Log.d(TAG, "Response unsuccessful: ${response.code()}")
                    updateToastMessage("Server error")
                    fetchAllLocalTransactions()
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
                updateToastMessage("Server error")
                fetchAllLocalTransactions()
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data: ${e.message}", e)
                updateToastMessage("Server error")
                fetchAllLocalTransactions()
            }
        }
    }

    private fun fetchDatesFromServer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = transactionAPI.getDates().execute()
                Log.d(TAG, "Data: ${response.body()}")

                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    Log.d(TAG, "Data: $data")

                    withContext(Dispatchers.Main) {
                        (dates as MutableLiveData).postValue(data)
                    }
                    Log.d(TAG, "Response successful: ${response.body()}")
                } else {
                    Log.d(TAG, "Response unsuccessful: ${response.code()}")
                    updateToastMessage("Server error")
                    fetchLocalDates()
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
                updateToastMessage("Server error")
                fetchLocalDates()

            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data: ${e.message}", e)
                updateToastMessage("Server error")
                fetchLocalDates()
            }
        }
    }

    fun fetchDateDetailsFromServer(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = transactionAPI.getTransactionsByDate(date).execute()

                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    Log.d(TAG, "Data: $data")

                    withContext(Dispatchers.Main) {
                        (transactions as MutableLiveData).postValue(data)
                    }
                    Log.d(TAG, "Response successful: ${response.body()}")
                } else {
                    Log.d(TAG, "Response unsuccessful: ${response.code()}")
                    updateToastMessage("Server error")
                    fetchLocalDateDetails(date)
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", e)
                updateToastMessage("Server error")
                fetchLocalDateDetails(date)
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data: ${e.message}", e)
                updateToastMessage("Server error")
                fetchLocalDateDetails(date)
            }
        }
    }

    fun createTransaction(
        date: String,
        type: String,
        amount: Double,
        category: String,
        description: String
    ) {
        val entity = Transaction(-1, date, type, amount, category, description)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = transactionAPI.createTransaction(entity).execute()

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

    fun deleteTransaction(entity: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = transactionAPI.deleteTransaction(entity.id).execute()

                if (response.isSuccessful) {
                    Log.d(TAG, "Delete successfully")
                    db.deleteTransaction(entity)
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

    fun getTopCategories(): List<Pair<String?, Int>> {

        // Group transactions by category and count the number of transactions for each category
        val transactionsList = transactions.value ?: emptyList()
        val categoryCountMap = transactionsList
            .groupBy { it.category }
            .mapValues { (_, transactions) -> transactions.size }

        // Sort the categories by the number of transactions in descending order
        val sortedCategories = categoryCountMap.entries.sortedByDescending { it.value }

        // Take the top 3 categories
        val topCategories = sortedCategories.take(3)

        return topCategories.map { it.key to it.value }
    }

     fun getWeeklyTotals(): Map<String, Double> {
        // Group transactions by week
        val transactionsList = transactions.value ?: emptyList()
        val weeklyTransactions = transactionsList.groupBy { getWeekFromDate(it.date) }

         // Calculate total amount for each week
         val weeklyTotals = mutableMapOf<String, Double>()

         for ((week, transactions) in weeklyTransactions) {
             var totalAmount = 0.0
             for (transaction in transactions) {
                 totalAmount += transaction.amount ?: 0.0
             }
             weeklyTotals[week] = totalAmount
         }

         return weeklyTotals
    }

    fun getWeekFromDate(dateString: String): String {
        // Parse the date string into a LocalDate object
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)

        // Get the week of year using ISO week definition
        val weekOfYear = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())

        return "Week $weekOfYear"
    }
}

class TransactionViewModelFactory(
    private val db: TransactionDao,
    private val transactionAPI: TransactionAPI
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionViewModel(
            db = db,
            transactionAPI = transactionAPI
        ) as T
    }
}