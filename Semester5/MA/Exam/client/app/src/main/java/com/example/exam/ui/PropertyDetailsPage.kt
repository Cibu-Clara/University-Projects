package com.example.exam.ui

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
import com.example.exam.Constants
import com.example.exam.viewmodel.PropertyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PropertyDetailsPage(
    id: Int,
    navController: NavController,
    propertyViewModel: PropertyViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val toastMessage by rememberUpdatedState(newValue = propertyViewModel.toastMessage.collectAsState().value)

    val property = remember {
        mutableStateOf(Constants.PROPERTY_PLACE_HOLDER)
    }

    if (toastMessage.isNotBlank()) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_LONG).show()
        propertyViewModel.clearToastMessage()
    }

    LaunchedEffect(true) {
        coroutineScope.launch(Dispatchers.IO) {
            property.value = propertyViewModel.getProperty(id) ?: Constants.PROPERTY_PLACE_HOLDER
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
                    text = "Name: ${property.value.name}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Date: ${property.value.date}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Details: ${property.value.details}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Status: ${property.value.status}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "Viewers: ${property.value.viewers}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Type: ${property.value.type}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}