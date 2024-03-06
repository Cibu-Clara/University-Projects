package com.example.exam.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.exam.viewmodel.PropertyViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddPropertyPage(
    navController: NavController,
    propertyViewModel: PropertyViewModel
) {
    val toastMessage by rememberUpdatedState(newValue = propertyViewModel.toastMessage.collectAsState().value)
    val coroutineScope = rememberCoroutineScope()
    var progressLoading by remember { mutableStateOf(false) }

    val currentName = remember {
        mutableStateOf("")
    }
    val currentDate = remember {
        mutableStateOf("")
    }
    val currentDetails = remember {
        mutableStateOf("")
    }
    val currentStatus = remember {
        mutableStateOf("")
    }
    val currentViewers = remember {
        mutableStateOf("")
    }
    val currentType = remember {
        mutableStateOf("")
    }

    if (toastMessage.isNotBlank()) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_LONG).show()
        propertyViewModel.clearToastMessage()
    }

    Surface(
        Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {}
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color.White)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                TextField(
                    value = currentName.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentName.value = value
                    },
                    label = { Text(text = "Enter Name") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentDate.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentDate.value = value
                    },
                    label = { Text(text = "Enter Date") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentDetails.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentDetails.value = value
                    },
                    label = { Text(text = "Enter Details") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentStatus.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentStatus.value = value
                    },
                    label = { Text(text = "Enter Status") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentViewers.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentViewers.value = value
                    },
                    label = { Text(text = "Enter Viewers") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentType.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentType.value = value
                    },
                    label = { Text(text = "Enter Type") }
                )
                Spacer(modifier = Modifier.padding(12.dp))
                Spacer(modifier = Modifier.padding(12.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    onClick = {
                        coroutineScope.launch {
                            progressLoading = true
                            try {
                                propertyViewModel.createProperty(
                                    name = currentName.value,
                                    date = currentDate.value,
                                    details = currentDetails.value,
                                    status = currentStatus.value,
                                    viewers = currentViewers.value.toInt(),
                                    type = currentType.value
                                )
                                awaitFrame()
                                progressLoading = false
                                navController.popBackStack()
                            } catch (e: Exception) {
                                propertyViewModel.updateToastMessage("Invalid fields!")
                                progressLoading = false
                            }
                        }
                    }
                ) {
                    Text(text = "ADD")
                }
            }
        }
    }
}