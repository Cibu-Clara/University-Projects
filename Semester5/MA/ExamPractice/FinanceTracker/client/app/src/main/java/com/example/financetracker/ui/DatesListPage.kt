package com.example.financetracker.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.financetracker.Constants
import com.example.financetracker.R
import com.example.financetracker.viewmodel.TransactionViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DatesListPage(
    navController: NavController,
    transactionViewModel: TransactionViewModel
) {
    val dates = transactionViewModel.dates.observeAsState()
    val toastMessage by rememberUpdatedState(newValue = transactionViewModel.toastMessage.collectAsState().value)

    var showNoInternet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var progressLoading by remember { mutableStateOf(false) }

    if (toastMessage.isNotBlank()) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_LONG).show()
        transactionViewModel.clearToastMessage()
    }

    Surface(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    ) {
                        Button(
                            modifier = Modifier.padding(start = 30.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            onClick = {
                                coroutineScope.launch {
                                    progressLoading = true
                                    navController.navigate(Constants.NAVIGATION_PROGRESS_SECTION)
                                    awaitFrame()
                                    progressLoading = false
                                }
                            },
                        ) {
                            androidx.compose.material3.Text(text = "Progress Section")
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            onClick = {
                                coroutineScope.launch {
                                    progressLoading = true
                                    navController.navigate(Constants.NAVIGATION_TOP_SECTION)
                                    awaitFrame()
                                    progressLoading = false
                                }
                            },
                        ) {
                            androidx.compose.material3.Text(text = "Top Section")
                        }
                    }
                    Row(
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    ) {
                        Button(
                            modifier = Modifier.padding(start = 30.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            onClick = {
                                coroutineScope.launch {
                                    progressLoading = true
                                    transactionViewModel.refreshAction()
                                    awaitFrame()
                                    progressLoading = false
                                }
                            },
                        ) {
                            androidx.compose.material3.Text(text = "REFRESH")
                        }
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            val isConnected = transactionViewModel.checkInternet()

                            if (isConnected) {
                                navController.navigate(Constants.NAVIGATION_CREATE_TRANSACTION)
                            } else {
                                showNoInternet = true
                            }
                        }
                    }
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.add_button),
                        contentDescription = "add",
                        tint = Color.Black
                    )
                }
            }
        ) {
            DatesList(
                dates = dates.value.orEmpty(),
                navController = navController,
                transactionViewModel
            )
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
    }

    if (progressLoading){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun DatesList(
    dates: List<String>,
    navController: NavController,
    transactionViewModel: TransactionViewModel
) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier.background(Color.White)
    ){
        itemsIndexed(dates) { _, t ->
            DateListItem(
                t,
                navController,
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
fun DateListItem(
    date: String,
    navController: NavController,
    transactionViewModel: TransactionViewModel
) {
    Box(modifier = Modifier
        .background(Color.LightGray)
        .height(60.dp)
        .clip(RoundedCornerShape(20.dp))
        .clickable {
            navController.navigate(Constants.dayDetailsNavigation(date))
            transactionViewModel.fetchDateDetailsFromServer(date)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(120.dp)
        ) {
            Text(
                text = date,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}