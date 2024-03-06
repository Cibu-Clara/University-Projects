package com.example.petowners.ui

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
import androidx.compose.material3.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petowners.viewmodel.PetViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddPetPage(
    navController: NavController,
    petViewModel: PetViewModel
) {
    val toastMessage by rememberUpdatedState(newValue = petViewModel.toastMessage.collectAsState().value)
    val coroutineScope = rememberCoroutineScope()
    var progressLoading by remember { mutableStateOf(false) }

    val currentName = remember {
        mutableStateOf("")
    }
    val currentBreed = remember {
        mutableStateOf("")
    }
    val currentAge = remember {
        mutableStateOf("")
    }
    val currentWeight = remember {
        mutableStateOf("")
    }
    val currentOwner = remember {
        mutableStateOf("")
    }
    val currentLocation = remember {
        mutableStateOf("")
    }
    val currentDescription = remember {
        mutableStateOf("")
    }

    if (toastMessage.isNotBlank()) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_LONG).show()
        petViewModel.clearToastMessage()
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
                    value = currentBreed.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentBreed.value = value
                    },
                    label = { Text(text = "Enter Breed") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentAge.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentAge.value = value
                    },
                    label = { Text(text = "Enter Age") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentWeight.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentWeight.value = value
                    },
                    label = { Text(text = "Enter Weight") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentOwner.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentOwner.value = value
                    },
                    label = { Text(text = "Enter Owner") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentLocation.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentLocation.value = value
                    },
                    label = { Text(text = "Enter Location") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentDescription.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value ->
                        currentDescription.value = value
                    },
                    label = { Text(text = "Enter Description") }
                )
                Spacer(modifier = Modifier.padding(12.dp))
                Spacer(modifier = Modifier.padding(12.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    onClick = {
                        coroutineScope.launch {
                            progressLoading = true
                            try {
                                petViewModel.createPet(
                                    name = currentName.value,
                                    breed = currentBreed.value,
                                    age = currentAge.value.toInt(),
                                    weight = currentWeight.value.toInt(),
                                    owner = currentOwner.value,
                                    location = currentLocation.value,
                                    description = currentDescription.value
                                )
                                awaitFrame()
                                progressLoading = false
                                navController.popBackStack()
                            } catch (e: Exception) {
                                petViewModel.updateToastMessage("Invalid fields!")
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