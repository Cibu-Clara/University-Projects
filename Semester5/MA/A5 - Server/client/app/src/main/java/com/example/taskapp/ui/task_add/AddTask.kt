package com.example.taskapp.ui.task_add

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskapp.R
import com.example.taskapp.ui.GenericAppBar
import com.example.taskapp.ui.theme.TaskAppTheme
import com.example.taskapp.viewmodel.TasksViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTaskPage(
    navController: NavController,
    viewModel: TasksViewModel
){
    val currentTitle = remember {
        mutableStateOf("")
    }
    val currentPriorityLevel = remember {
        mutableStateOf("")
    }
    val currentDateDeadline = remember {
        mutableStateOf("")
    }
    val currentTimeDeadline = remember {
        mutableStateOf("")
    }
    val currentDescription = remember {
        mutableStateOf("")
    }
    val saveButtonState = remember {
        mutableStateOf(false)
    }

    TaskAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            Scaffold (
                topBar = { GenericAppBar(
                    title = "Add Task",
                    onIconClick = {
                        viewModel.createTask(
                            currentTitle.value,
                            currentPriorityLevel.value,
                            currentDateDeadline.value,
                            currentTimeDeadline.value,
                            currentDescription.value
                        )
                        navController.popBackStack()
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_save_24),
                            contentDescription = stringResource(R.string.save_task),
                            tint = Color.White
                        )
                    },
                    iconState = saveButtonState
                )}
            ){
                Column(modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()) {
                    TextField(
                        value = currentTitle.value,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentTitle.value = value
                            saveButtonState.value = currentTitle.value != "" && currentDescription.value != ""
                        },
                        label = { Text("Title") }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        value = currentPriorityLevel.value,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentPriorityLevel.value = value
                        },
                        label = { Text("Priority Level") }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        value = currentDateDeadline.value,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentDateDeadline.value = value
                        },
                        label = { Text("Date Deadline") }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        value = currentTimeDeadline.value,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentTimeDeadline.value = value
                        },
                        label = { Text("Time Deadline") }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        value = currentDescription.value,
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentDescription.value = value
                            saveButtonState.value = currentTitle.value != "" && currentDescription.value != ""
                        },
                        label = { Text("Description") }
                    )
                }
            }

        }
    }
}