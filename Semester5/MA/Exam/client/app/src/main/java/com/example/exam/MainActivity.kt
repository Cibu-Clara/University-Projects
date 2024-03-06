package com.example.exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.exam.ui.AddPropertyPage
import com.example.exam.ui.PropertiesListPage
import com.example.exam.ui.PropertyDetailsPage
import com.example.exam.ui.TypesListPage
import com.example.exam.viewmodel.PropertyViewModel
import com.example.exam.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var propertyViewModel: PropertyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        propertyViewModel = ViewModelFactory(App.getDao(), App.getAPI()).create(PropertyViewModel::class.java)

        setContent {
            val navController = rememberNavController()

            // Properties List
            NavHost(navController = navController, startDestination = Constants.NAVIGATION_PROPERTIES_LIST) {
                composable(Constants.NAVIGATION_PROPERTIES_LIST) {
                    PropertiesListPage(
                        navController,
                        propertyViewModel
                    )
                }

                // Property Details
                composable(
                    Constants.NAVIGATION_PROPERTY_DETAILS,
                    arguments = listOf(navArgument(Constants.NAVIGATION_ARGUMENT) {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getInt(Constants.NAVIGATION_ARGUMENT)?.let {
                        PropertyDetailsPage(
                            id = it,
                            navController = navController,
                            propertyViewModel = propertyViewModel
                        )
                    }
                }

                // Add Property
                composable(Constants.NAVIGATION_CREATE_PROPERTY) {
                    AddPropertyPage(
                        navController,
                        propertyViewModel
                    )
                }

                //Client Section
                composable(Constants.NAVIGATION_CLIENT_SECTION) {
                    TypesListPage(
                        navController,
                        propertyViewModel
                    )
                }
            }
        }
    }
}