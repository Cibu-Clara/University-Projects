package com.example.financetracker.ui

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
import com.example.financetracker.viewmodel.TransactionViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTransactionPage(
    navController: NavController,
    transactionViewModel: TransactionViewModel
) {
    val toastMessage by rememberUpdatedState(newValue = transactionViewModel.toastMessage.collectAsState().value)
    val coroutineScope = rememberCoroutineScope()
    var progressLoading by remember { mutableStateOf(false) }

    if (toastMessage.isNotBlank()) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_LONG).show()
        transactionViewModel.clearToastMessage()
    }

    val currentDate = remember {
        mutableStateOf("")
    }
    val currentType = remember {
        mutableStateOf("")
    }
    val currentAmount = remember {
        mutableStateOf("")
    }

    val currentCategory = remember {
        mutableStateOf("")

    }
    val currentDescription = remember {
        mutableStateOf("")
    }

    Surface(
        Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color.White)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                TextField(
                    value = currentDate.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value -> currentDate.value = value },
                    label = { Text(text = "Enter Date") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentType.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value -> currentType.value = value },
                    label = { Text(text = "Enter Type") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentAmount.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = { value -> currentAmount.value = value },
                    label = { Text(text = "Enter Amount") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentCategory.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value -> currentCategory.value = value },
                    label = { Text(text = "Enter Category") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                TextField(
                    value = currentDescription.value,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    onValueChange = { value -> currentDescription.value = value },
                    label = { Text(text = "Enter Description") }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                Button(colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    onClick = {
                        coroutineScope.launch {
                            progressLoading = true
                            try {
                                transactionViewModel.createTransaction(
                                    date = currentDate.value,
                                    type = currentType.value,
                                    amount = currentAmount.value.toDouble(),
                                    category = currentCategory.value,
                                    description = currentDescription.value,
                                )
                                awaitFrame()
                                progressLoading = false
                                navController.popBackStack()
                            } catch (e: Exception) {
                                transactionViewModel.updateToastMessage("Invalid Fields!")
                                progressLoading = false
                            }
                        }
                    }) {
                    Text(text = "ADD")
                }
            }
        }
    }
}