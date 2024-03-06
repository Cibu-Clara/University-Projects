package com.example.petowners

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.petowners.ui.AddPetPage
import com.example.petowners.ui.PetDetailsPage
import com.example.petowners.ui.PetsListPage
import com.example.petowners.viewmodel.PetViewModel
import com.example.petowners.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var petViewModel: PetViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        petViewModel = ViewModelFactory(App.getDao(), App.getApi()).create(PetViewModel::class.java)
        
        setContent {
            val navController = rememberNavController()
            
            NavHost(navController = navController, startDestination = Constants.NAVIGATION_PETS_LIST) {
                // Pets List
                composable(Constants.NAVIGATION_PETS_LIST) {
                    PetsListPage(
                        navController,
                        petViewModel
                    )
                }

                // Pet Details
                composable(
                    Constants.NAVIGATION_PET_DETAILS,
                    arguments = listOf(navArgument(Constants.NAVIGATION_ARGUMENT) {
                        type = NavType.IntType
                    })
                ) {backStackEntry ->
                    backStackEntry.arguments?.getInt(Constants.NAVIGATION_ARGUMENT)?.let {
                        PetDetailsPage(
                            id = it,
                            navController = navController,
                            petViewModel = petViewModel
                        )
                    }
                }

                // Add Pet
                composable(Constants.NAVIGATION_ADD_PET) {
                    AddPetPage(
                        navController,
                        petViewModel
                    )
                }
            }
        }
    }
}