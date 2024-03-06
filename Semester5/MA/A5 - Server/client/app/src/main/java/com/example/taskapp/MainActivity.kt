package com.example.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskapp.ui.task_add.AddTaskPage
import com.example.taskapp.ui.task_details.TaskDetailsPage
import com.example.taskapp.ui.task_edit.EditTaskPage
import com.example.taskapp.ui.task_list.TasksListPage
import com.example.taskapp.utils.Constants
import com.example.taskapp.viewmodel.TaskViewModelFactory
import com.example.taskapp.viewmodel.TasksViewModel

class  MainActivity : ComponentActivity() {

    private lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = TaskViewModelFactory(TasksApp.getDao(), TasksApp.getAPI()).create(TasksViewModel::class.java)

        setContent{
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Constants.NAVIGATION_TASKS_LIST) {
                // Tasks List
                composable(Constants.NAVIGATION_TASKS_LIST) { TasksListPage(
                    navController = navController,
                    viewModel = viewModel
                )}

                // Task Details
                composable(
                    Constants.NAVIGATION_TASK_DETAILS,
                    arguments = listOf(navArgument(Constants.NAVIGATION_TASK_ID_ARGUMENT) {
                        type = NavType.IntType
                    })
                ) {navBackStackEntry ->
                    navBackStackEntry.arguments?.getInt(Constants.NAVIGATION_TASK_ID_ARGUMENT)?.let {
                        TaskDetailsPage(taskId = it, navController = navController, viewModel = viewModel)
                    }
                }

                //  Edit Task
                composable(
                    Constants.NAVIGATION_TASK_EDIT,
                    arguments = listOf(navArgument(Constants.NAVIGATION_TASK_ID_ARGUMENT) {
                        type = NavType.IntType
                    })
                ) {navBackStackEntry ->
                    navBackStackEntry.arguments?.getInt(Constants.NAVIGATION_TASK_ID_ARGUMENT)?.let {
                        EditTaskPage(taskId = it, navController = navController, viewModel = viewModel)
                    }
                }

                // Add Task
                composable(Constants.NAVIGATION_TASK_ADD) {
                    AddTaskPage(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}