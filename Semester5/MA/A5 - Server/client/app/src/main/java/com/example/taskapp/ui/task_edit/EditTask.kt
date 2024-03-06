package com.example.taskapp.ui.task_edit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskapp.utils.Constants
import com.example.taskapp.R
import com.example.taskapp.data.model.Task
import com.example.taskapp.ui.GenericAppBar
import com.example.taskapp.ui.theme.TaskAppTheme
import com.example.taskapp.viewmodel.TasksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditTaskPage(
    taskId: Int,
    navController: NavController,
    viewModel: TasksViewModel
){
    val scope = rememberCoroutineScope()
    val task = remember {
        mutableStateOf(Constants.taskDetailsPlaceholder)
    }
    val currentTitle = remember {
        mutableStateOf(task.value.title)
    }
    val currentPriorityLevel = remember {
        mutableStateOf(task.value.priorityLevel)
    }
    val currentDateDeadline = remember {
        mutableStateOf(task.value.dateDeadline)
    }
    val currentTimeDeadline = remember {
        mutableStateOf(task.value.timeDeadline)
    }
    val currentDescription = remember {
        mutableStateOf(task.value.description)
    }
    val saveButtonState = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            task.value = viewModel.getTask(taskId) ?: Constants.taskDetailsPlaceholder
            currentTitle.value = task.value.title
            currentPriorityLevel.value = task.value.priorityLevel
            currentDateDeadline.value = task.value.dateDeadline
            currentTimeDeadline.value = task.value.timeDeadline
            currentDescription.value = task.value.description
        }
    }

    TaskAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            Scaffold (
                topBar = { GenericAppBar(
                    title = "Edit Task",
                    onIconClick = {
                        viewModel.updateTask(
                            Task(
                                id = task.value.id,
                                title = currentTitle.value,
                                priorityLevel = currentPriorityLevel.value,
                                dateDeadline = currentDateDeadline.value,
                                timeDeadline = currentTimeDeadline.value,
                                description = currentDescription.value
                            )
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
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentTitle.value = value
                            if (currentTitle.value != task.value.title) {
                                saveButtonState.value = true
                            } else if (currentDescription.value == task.value.description &&
                                currentTitle.value == task.value.title) {
                                saveButtonState.value = false
                            }
                        },
                        label = {Text("Title")}
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        value = currentPriorityLevel.value,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentPriorityLevel.value = value
                        },
                        label = {Text("Priority Level")}
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        value = currentDateDeadline.value,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentDateDeadline.value = value
                        },
                        label = {Text("Date Deadline")}
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        value = currentTimeDeadline.value,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentTimeDeadline.value = value
                        },
                        label = {Text("Time Deadline")}
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
                            if (currentDescription.value != task.value.description) {
                                saveButtonState.value = true
                            } else if (currentDescription.value == task.value.description &&
                                currentTitle.value == task.value.title) {
                                saveButtonState.value = false
                            }
                        },
                        label = {Text("Description")}
                    )
                }
            }

        }
    }
}