package com.example.exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.exam.ui.AddPage
import com.example.exam.ui.DetailsPage
import com.example.exam.ui.EditPage
import com.example.exam.ui.ListPage
import com.example.exam.view_model.EntityViewModel
import com.example.exam.view_model.ViewModelFactory


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: EntityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelFactory(App.getDao(), App.getAPI()).create(EntityViewModel::class.java)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Constants.NAVIGATION_LIST) {
                composable(Constants.NAVIGATION_LIST) {
                    ListPage(
                        navController,
                        viewModel
                    )
                }

                composable(
                    Constants.NAVIGATION_EDIT,
                    arguments = listOf(navArgument(Constants.NAVIGATION_ARGUMENT) {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getInt(Constants.NAVIGATION_ARGUMENT)?.let {
                        EditPage(
                            id = it,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }

                composable(
                    Constants.NAVIGATION_DETAILS,
                    arguments = listOf(navArgument(Constants.NAVIGATION_ARGUMENT) {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getInt(Constants.NAVIGATION_ARGUMENT)?.let {
                        DetailsPage(
                            id = it,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }

                composable(Constants.NAVIGATION_CREATE) {
                    AddPage(
                        navController,
                        viewModel
                    )
                }
            }
        }
    }
}
