package com.example.exam.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
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
import com.example.exam.Constants
import com.example.exam.R
import com.example.exam.model.Property
import com.example.exam.viewmodel.PropertyViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PropertiesListPage(
    navController: NavController,
    propertyViewModel: PropertyViewModel
) {
    val properties = propertyViewModel.properties.observeAsState()
    val toastMessage by rememberUpdatedState(newValue = propertyViewModel.toastMessage.collectAsState().value)

    var showNoInternet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var progressLoading by remember { mutableStateOf(false) }

    if (toastMessage.isNotBlank()) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_LONG).show()
        propertyViewModel.clearToastMessage()
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
                                    navController.navigate(Constants.NAVIGATION_CLIENT_SECTION)
                                    propertyViewModel.fetchTypesFromServer()
                                    awaitFrame()
                                    progressLoading = false
                                }
                            },
                        ) {
                            Text(text = "Client Section")
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            onClick = {
                                coroutineScope.launch {
                                    progressLoading = true
                                    navController.navigate(Constants.NAVIGATION_ADMIN_SECTION)
                                    awaitFrame()
                                    progressLoading = false
                                }
                            },
                        ) {
                            Text(text = "Admin Section")
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
                                    propertyViewModel.refreshAction()
                                    awaitFrame()
                                    progressLoading = false
                                }
                            },
                        ) {
                            Text(text = "REFRESH")
                        }
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            val isConnected = propertyViewModel.checkInternet()

                            if (isConnected) {
                                navController.navigate(Constants.NAVIGATION_CREATE_PROPERTY)
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
            PropertiesList(
                properties = properties.value.orEmpty(),
                navController = navController,
                propertyViewModel
            )
            if (showNoInternet) {
                AlertDialog(
                    onDismissRequest = {
                        showNoInternet = false
                    },
                    title = {
                        androidx.compose.material.Text("No server connection")
                    },
                    text = {
                        androidx.compose.material.Text("Cannot perform this operation !")
                    },
                    confirmButton = {

                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                showNoInternet = false
                            }
                        ) {
                            androidx.compose.material.Text("Cancel")
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
fun PropertiesList(
    properties: List<Property>,
    navController: NavController,
    propertyViewModel: PropertyViewModel
) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier.background(Color.White)
    ){
        itemsIndexed(properties) { _, p ->
            PropertyListItem(
                p,
                navController,
                propertyViewModel
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
fun PropertyListItem(
    property: Property,
    navController: NavController,
    propertyViewModel: PropertyViewModel
) {
    Box(modifier = Modifier
        .background(Color.LightGray)
        .height(120.dp)
        .clip(RoundedCornerShape(20.dp))
        .clickable {
            navController.navigate(Constants.propertyDetailsNavigation(property.id))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(120.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material.Text(
                    text = "${property.id}." + property.name,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material.Text(
                    text = "Date: ${property.date}",
                    color = Color.Black,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material.Text(
                    text = "Type: ${property.type}",
                    color = Color.Black,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    }
}