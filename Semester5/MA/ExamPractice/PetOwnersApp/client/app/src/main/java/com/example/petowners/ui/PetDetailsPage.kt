package com.example.petowners.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petowners.Constants
import com.example.petowners.viewmodel.PetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PetDetailsPage(
    id: Int,
    navController: NavController,
    petViewModel: PetViewModel
) {
    val pet = remember { mutableStateOf(Constants.PET_DETAILS_PLACEHOLDER) }
    val toastMessage by rememberUpdatedState(newValue = petViewModel.toastMessage.collectAsState().value)

    val coroutineScope = rememberCoroutineScope()

    if (toastMessage.isNotBlank()) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_LONG).show()
        petViewModel.clearToastMessage()
    }

    LaunchedEffect(true) {
        coroutineScope.launch(Dispatchers.IO) {
            pet.value = petViewModel.getPet(id) ?: Constants.PET_DETAILS_PLACEHOLDER
        }
    }

    Surface(
        Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            topBar = {}
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color.White)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Name: ${pet.value.name}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Breed: ${pet.value.breed}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Age: ${pet.value.age}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Weight: ${pet.value.weight}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Owner: ${pet.value.owner}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Location: ${pet.value.location}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Description: ${pet.value.description}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}