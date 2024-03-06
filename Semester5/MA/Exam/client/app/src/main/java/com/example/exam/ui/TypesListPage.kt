package com.example.exam.ui

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
import com.example.exam.viewmodel.PropertyViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TypesListPage(
    navController: NavController,
    propertyViewModel: PropertyViewModel
) {
    val types = propertyViewModel.types.observeAsState()
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
        Scaffold {
            TypesList(
                types = types.value.orEmpty(),
                navController = navController,
                propertyViewModel
            )
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
fun TypesList(
    types: List<String>,
    navController: NavController,
    propertyViewModel: PropertyViewModel
) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier.background(Color.White)
    ){
        itemsIndexed(types) { _, t ->
            TypeListItem(
                t,
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
fun TypeListItem(
    type: String,
    navController: NavController,
    propertyViewModel: PropertyViewModel
) {
    Box(modifier = Modifier
        .background(Color.LightGray)
        .height(60.dp)
        .clip(RoundedCornerShape(20.dp))
        .clickable {
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(120.dp)
        ) {
            Text(
                text = type,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}