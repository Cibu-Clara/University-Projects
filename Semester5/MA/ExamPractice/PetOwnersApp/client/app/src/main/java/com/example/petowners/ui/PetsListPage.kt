package com.example.petowners.ui

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petowners.Constants
import com.example.petowners.R
import com.example.petowners.model.Pet
import com.example.petowners.viewmodel.PetViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PetsListPage(
    navController: NavController,
    petViewModel: PetViewModel
) {
    val pets = petViewModel.pets.observeAsState()
    val toastMessage by rememberUpdatedState(newValue = petViewModel.toastMessage.collectAsState().value)

    var showNoInternet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var progressLoading by remember { mutableStateOf(false) }

    val currentBreed = remember {
        mutableStateOf("")
    }
    val currentAge = remember {
        mutableStateOf("")
    }
    val currentLocation = remember {
        mutableStateOf("")
    }
    
    if (toastMessage.isNotBlank()) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_LONG).show()
        petViewModel.clearToastMessage()
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
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = currentBreed.value, 
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ), 
                        onValueChange = { value ->
                            currentBreed.value = value
                        },
                        label = { Text(text = "Enter Breed")}
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = currentAge.value,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentAge.value = value
                        },
                        label = { Text(text = "Enter Age")},
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = currentLocation.value,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentLocation.value = value
                        },
                        label = { Text(text = "Enter Location")}
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        modifier = Modifier.align(alignment = CenterHorizontally)
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            onClick = {
                                coroutineScope.launch {
                                    progressLoading = true
                                    petViewModel.filterPets(currentBreed.value, currentAge.value, currentLocation.value)
                                    awaitFrame()
                                    progressLoading = false
                                }
                            }
                        ) {
                            androidx.compose.material3.Text(text = "FILTER")
                        }
                        Spacer(modifier = Modifier.padding(5.dp))

                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            onClick = {
                                coroutineScope.launch {
                                    progressLoading = true
                                    petViewModel.refreshAction()
                                    awaitFrame()
                                    progressLoading = false
                                }
                            }
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
                            val isConnected = petViewModel.checkInternet()

                            if (isConnected) {
                                navController.navigate(Constants.NAVIGATION_ADD_PET)
                            } else {
                                showNoInternet = true
                            }
                        }
                    }
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.baseline_add_24),
                        contentDescription = "add",
                        tint = Color.Black
                    )
                }
            }
        ) {
            PetsList(
                pets = pets.value.orEmpty(),
                navController = navController,
                petViewModel = petViewModel
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
                        Text("Cannot perform this operation!")
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

    if (progressLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun PetsList(
    pets: List<Pet>,
    navController: NavController,
    petViewModel: PetViewModel
) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier.background(Color.White)
    ) {
        itemsIndexed(pets) {_, pet ->
            PetListItem (
                pet,
                navController,
                petViewModel
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
fun PetListItem(
    pet: Pet,
    navController: NavController,
    petViewModel: PetViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    var showNoInternet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .height(80.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { navController.navigate(Constants.petDetailsNavigation(pet.id)) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(120.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Name: " + pet.name,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
                Column {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val isConnected = petViewModel.checkInternet()

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
                Text(pet.name)
            },
            text = {
                Text("Are you sure you want to delete this pet?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        petViewModel.deletePet(pet)
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
                Text("Cannot perform this operation!")
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
