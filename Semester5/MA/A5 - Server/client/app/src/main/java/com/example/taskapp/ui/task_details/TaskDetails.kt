package com.example.taskapp.ui.task_details

import androidx.compose.material.AlertDialog
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskapp.utils.Constants
import com.example.taskapp.utils.Constants.taskDetailsPlaceholder
import com.example.taskapp.R
import com.example.taskapp.ui.GenericAppBar
import com.example.taskapp.ui.theme.TaskAppTheme
import com.example.taskapp.viewmodel.TasksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskDetailsPage(taskId: Int, navController: NavController, viewModel: TasksViewModel) {
    val scope = rememberCoroutineScope()

    val task = remember {
        mutableStateOf(taskDetailsPlaceholder)
    }
    var showNoInternet by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            task.value = viewModel.getTask(taskId) ?: taskDetailsPlaceholder
        }
    }

    TaskAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            Scaffold (
                topBar = { GenericAppBar(
                    title = "Task Details",
                    onIconClick = {
                        if(viewModel.isConnected.value != true){
                            showNoInternet = true
                        }
                        else{
                            navController.navigate(Constants.taskEditNavigation(task.value.id ?: 0))
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_edit_24),
                            contentDescription = stringResource(R.string.edit_task),
                            tint = Color.White
                        )
                    },
                    iconState = remember {
                        mutableStateOf(true)
                    }
                )}
            ){
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = task.value.title,
                        modifier = Modifier.padding(top=24.dp, start=24.dp, end=24.dp),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Priority Level: " + task.value.priorityLevel,
                        modifier = Modifier.padding(12.dp)
                    )
                    Text(
                        text = "Date Deadline: " + task.value.dateDeadline,
                        modifier = Modifier.padding(12.dp)
                    )
                    Text(
                        text = "Time Deadline: " + task.value.timeDeadline,
                        modifier = Modifier.padding(12.dp)
                    )
                    Text(
                        text = task.value.description,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                if (showNoInternet) {
                    AlertDialog(
                        onDismissRequest = {
                            showNoInternet = false
                        },
                        title = {
                            Text("No internet connection")
                        },
                        text = {
                            Text("Can't perform this operation !")
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
    }
}