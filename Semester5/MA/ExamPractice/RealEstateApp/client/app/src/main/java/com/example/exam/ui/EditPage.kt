package com.example.exam.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.exam.Constants
import com.example.exam.R
import com.example.exam.model.Property
import com.example.exam.view_model.EntityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditPage(
    id: Int,
    navController: NavController,
    viewModel: EntityViewModel
) {
    var progressLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val toastMessage by rememberUpdatedState(newValue = viewModel.toastMessage.collectAsState().value)


    val entity = remember {
        mutableStateOf(Constants.ENTITY_PLACE_HOLDER)
    }
    val currentDate = remember {
        mutableStateOf(entity.value.date)
    }
    val currentType = remember {
        mutableStateOf(entity.value.type)
    }
    val currentAddress = remember {
        mutableStateOf(entity.value.address)
    }
    val currentBedrooms = remember {
        mutableStateOf(entity.value.bedrooms.toString())
    }
    val currentBathrooms = remember {
        mutableStateOf(entity.value.bathrooms.toString())
    }
    val currentArea = remember {
        mutableStateOf(entity.value.area.toString())
    }
    val currentPrice = remember {
        mutableStateOf(entity.value.price.toString())
    }
    val currentNotes = remember {
        mutableStateOf(entity.value.notes)
    }

    if (toastMessage.isNotBlank()) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_LONG).show()
        viewModel.clearToastMessage()
    }

    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            entity.value = viewModel.getEntity(id) ?: Constants.ENTITY_PLACE_HOLDER
            currentDate.value = entity.value.date ?: ""
            currentType.value = entity.value.type ?: ""
            currentAddress.value = entity.value.address
            currentBedrooms.value = entity.value.bedrooms.toString()
            currentBathrooms.value = entity.value.bathrooms.toString()
            currentPrice.value = entity.value.price.toString()
            currentArea.value = entity.value.area.toString()
            currentNotes.value = entity.value.notes ?: ""
        }
    }

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = { }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color.White)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                currentDate.value?.let { it1 ->
                    TextField(
                        value = it1,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentDate.value = value
                        },
                        label = { Text(text = "Date") }
                    )
                }
                Spacer(modifier = Modifier.padding(12.dp))
                currentType.value?.let { it1 ->
                    TextField(
                        value = it1,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentType.value = value
                        },
                        label = { Text(text = "Type") }
                    )
                }
                Spacer(modifier = Modifier.padding(12.dp))
                TextField(
                    value = currentAddress.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentAddress.value = value
                    },
                    label = { Text(text = "Address") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentBedrooms.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = { value ->
                        currentBedrooms.value = value
                    },
                    label = { Text(text = "Bedrooms") }
                )
                Spacer(modifier = Modifier.padding(12.dp))
                TextField(
                    value = currentBathrooms.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = { value ->
                        currentBathrooms.value = value
                    },
                    label = { Text(text = "Bathrooms") }
                )
                Spacer(modifier = Modifier.padding(12.dp))
                TextField(
                    value = currentPrice.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = { value ->
                        currentPrice.value = value
                    },
                    label = { Text(text = "Price") }
                )
                Spacer(modifier = Modifier.padding(12.dp))
                TextField(
                    value = currentArea.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = { value ->
                        currentArea.value = value
                    },
                    label = { Text(text = "Area") }
                )
                Spacer(modifier = Modifier.padding(12.dp))
                currentNotes.value?.let { it1 ->
                    TextField(
                        value = it1,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentNotes.value = value
                        },
                        label = { Text(text = "Notes") }
                    )
                }
                Spacer(modifier = Modifier.padding(12.dp))
                Spacer(modifier = Modifier.padding(12.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    onClick = {
                        scope.launch {
                            progressLoading = true
                            try {
                                viewModel.updateEntity(
                                    Property(
                                        id = entity.value.id,
                                        date = currentDate.value ?: "",
                                        type = currentType.value ?: "",
                                        address = currentAddress.value,
                                        bedrooms = currentBedrooms.value.toInt(),
                                        bathrooms = currentBathrooms.value.toInt(),
                                        price = currentPrice.value.toDouble(),
                                        area = currentArea.value.toInt(),
                                        notes = currentNotes.value ?: ""
                                    )
                                )
                                awaitFrame()
                                progressLoading = false
                                navController.popBackStack()
                            } catch (e: Exception) {
                                viewModel.updateToastMessage("Invalid Fields!")
                                progressLoading = false
                            }
                        }

                    }
                ) {
                    Text(text = "SAVE")
                }
            }
        }
    }
    if (progressLoading) {
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