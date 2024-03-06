package com.example.taskapp.ui.task_list

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskapp.Constants
import com.example.taskapp.R
import com.example.taskapp.model.Task
import com.example.taskapp.model.orPlaceHolderList
import com.example.taskapp.ui.GenericAppBar
import com.example.taskapp.ui.theme.TaskAppTheme
import com.example.taskapp.ui.theme.taskBGBlue
import com.example.taskapp.ui.theme.taskBGYellow
import com.example.taskapp.viewmodel.TasksViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TasksListPage(
    navController: NavController,
    viewModel: TasksViewModel
) {
    val deleteText = remember {
        mutableStateOf("")
    }
    val tasksToDelete = remember {
        mutableStateOf(listOf<Task>())
    }
    val openDialog = remember {
        mutableStateOf(false)
    }
    val tasks = viewModel.tasks.observeAsState()
    val context = LocalContext.current

    TaskAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.primary) {
            Scaffold(
                topBar = {
                    GenericAppBar(
                        title = stringResource(id = R.string.tasks),
                        onIconClick = {
                            if (tasks.value?.isNotEmpty() == true) {
                                openDialog.value = true
                                deleteText.value = "Are you sure you want to delete all tasks?"
                                tasksToDelete.value = tasks.value ?: emptyList()
                            } else {
                                Toast.makeText(context, "No tasks found", Toast.LENGTH_SHORT).show()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_delete_24),
                                contentDescription = stringResource(R.string.delete_task),
                                tint = Color.Black
                            )
                        },
                        iconState = remember{
                            mutableStateOf(true)
                        }
                    )
                },
                floatingActionButton = {
                    TasksFab(
                        contentDescription = stringResource(id = R.string.add_task),
                        action = {
                            navController.navigate(Constants.NAVIGATION_TASK_ADD)
                        },
                        icon = R.drawable.baseline_add_24
                    )
                }
            ) {
                TasksList(
                    tasks = tasks.value.orPlaceHolderList(),
                    openDialog = openDialog,
                    deleteText = deleteText,
                    navController = navController,
                    tasksToDelete = tasksToDelete
                )
                DeleteDialog(
                    openDialog = openDialog,
                    text = deleteText,
                    action = {
                        tasksToDelete.value.forEach{
                            viewModel.deleteTask(it)
                        }
                    },
                    tasksToDelete = tasksToDelete
                )
            }
        }
    }
}

@Composable
fun TasksList(
    tasks : List<Task>,
    openDialog : MutableState<Boolean>,
    deleteText : MutableState<String>,
    navController: NavController,
    tasksToDelete : MutableState<List<Task>>
) {
    LazyColumn(contentPadding = PaddingValues(12.dp), modifier = Modifier.background(Color.White)) {

        itemsIndexed(tasks) {index, task ->
            TaskListItem(
                task,
                openDialog,
                deleteText,
                navController,
                if (index % 2 == 0) {
                    taskBGYellow
                } else {
                    taskBGBlue
                },
                tasksToDelete
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(12.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskListItem(
    task: Task,
    openDialog: MutableState<Boolean>,
    deleteText: MutableState<String>,
    navController: NavController,
    taskBackground: Color,
    tasksToDelete: MutableState<List<Task>>
) {
    Box(modifier = Modifier
        .height(120.dp)
        .clip(RoundedCornerShape(12.dp))) {
        Column(modifier = Modifier
            .background(taskBackground)
            .height(120.dp)
            .fillMaxWidth()
            .combinedClickable(interactionSource = remember {
                MutableInteractionSource()
            },
                indication = rememberRipple(bounded = false),
                onClick = {
                    if (task.id != 0) {
                        navController.navigate(
                            Constants.taskDetailsNavigation(
                                taskId = task.id ?: 0
                            )
                        )
                    }
                },
                onLongClick = {
                    if (task.id != 0) {
                        openDialog.value = true
                        deleteText.value = "Are you sure you want to delete this task?"
                        tasksToDelete.value = mutableListOf(task)
                    }
                }
            )
        ) {
            Row {
                Column {
                    Text(
                        text = task.title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        style = TextStyle(fontSize = 30.sp) // Adjust the font size as needed
                    )
                    Text(
                        text = "Priority Level: " + task.priorityLevel,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Text(
                        text = "Date Deadline: " + task.dateDeadline,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Text(
                        text = "Time Deadline: " + task.timeDeadline,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(
                        text = task.description,
                        color = Color.Black,
                        maxLines = 3,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TasksFab(
    contentDescription: String,
    icon: Int,
    action: () -> Unit
) {
    return FloatingActionButton(onClick = { action.invoke() }, backgroundColor = MaterialTheme.colors.primary) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription,
            tint = Color.Black
        )
    }
}

@Composable
fun DeleteDialog(
    openDialog: MutableState<Boolean>,
    text: MutableState<String>,
    action: () -> Unit,
    tasksToDelete: MutableState<List<Task>>
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = {
                Text("Delete Task")
            },
            text = {
                Column {
                    Text(text.value)
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black,
                                contentColor = Color.White
                            ),
                            onClick = {
                                action.invoke()
                                openDialog.value = false
                                tasksToDelete.value = mutableListOf()
                            }
                        ) {
                            Text("Yes")
                        }
                        Spacer(modifier = Modifier.padding(12.dp))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black,
                                contentColor = Color.White
                            ),
                            onClick = {
                                openDialog.value = false
                                tasksToDelete.value = mutableListOf()
                            }
                        ) {
                            Text("No")
                        }
                    }
                }
            }
        )
    }
}