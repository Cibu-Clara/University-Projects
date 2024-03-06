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
import androidx.compose.material.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.exam.Constants
import com.example.exam.view_model.EntityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsPage(id: Int, navController: NavController, viewModel: EntityViewModel) {
    val scope = rememberCoroutineScope()
    val toastMessage by rememberUpdatedState(newValue = viewModel.toastMessage.collectAsState().value)

    val entity = remember {
        mutableStateOf(Constants.ENTITY_PLACE_HOLDER)
    }

    if (toastMessage.isNotBlank()) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_LONG).show()
        viewModel.clearToastMessage()
    }

    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            entity.value = viewModel.getEntity(id) ?: Constants.ENTITY_PLACE_HOLDER
        }
    }

    Surface(
        Modifier.fillMaxSize(),
        color = Color.White
    ) {
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
                Text(
                    text = "Address: ${entity.value.address}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Date: ${entity.value.date}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Type: ${entity.value.type}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Bedrooms: ${entity.value.bedrooms}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Bathrooms: ${entity.value.bathrooms}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Price: ${entity.value.price}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Area: ${entity.value.area}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Notes: ${entity.value.notes}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}