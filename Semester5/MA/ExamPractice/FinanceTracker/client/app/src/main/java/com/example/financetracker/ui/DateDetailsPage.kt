package com.example.financetracker.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.financetracker.model.Transaction
import com.example.financetracker.viewmodel.TransactionViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DateDetailsPage(
    transactionViewModel: TransactionViewModel
) {
    val transactions = transactionViewModel.transactions.observeAsState()
    val toastMessage by rememberUpdatedState(newValue = transactionViewModel.toastMessage.collectAsState().value)

    if (toastMessage.isNotBlank()) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_LONG).show()
        transactionViewModel.clearToastMessage()
    }

    Surface(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Scaffold {
            TransactionsList(
                transactions = transactions.value.orEmpty(),
                transactionViewModel = transactionViewModel
            )
        }
    }
}

@Composable
fun TransactionsList(
    transactions: List<Transaction>,
    transactionViewModel: TransactionViewModel
) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier.background(Color.White)
    ) {
        itemsIndexed(transactions) { _, t ->
            TransactionListItem(
                t,
                transactionViewModel
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            )
        }
    }
}

@Composable
fun TransactionListItem(
    transaction: Transaction,
    transactionViewModel: TransactionViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    var showNoInternet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier
        .background(Color.LightGray)
        .height(120.dp)
        .clip(RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(120.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Date: " + transaction.date,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(
                        text = "Type: " + transaction.type,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(
                        text = "Amount: " + transaction.amount,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(
                        text = "Category: " + transaction.category,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(
                        text = "Description: " + transaction.description,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
                Column {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val isConnected = transactionViewModel.checkInternet()

                                if (isConnected) {
                                    showDialog = true
                                } else {
                                    showNoInternet = true
                                }
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(transaction.date)
            },
            text = {
                Text("Are you sure you want to delete this transaction?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        transactionViewModel.deleteTransaction(transaction)
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showNoInternet) {
        AlertDialog(
            onDismissRequest = {
                showNoInternet = false
            },
            title = {
                Text("No server connection")
            },
            text = {
                Text("Can't perform this operation !")
            },
            confirmButton = {

            },
            dismissButton = {
                Button(
                    onClick = {
                        showNoInternet = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}