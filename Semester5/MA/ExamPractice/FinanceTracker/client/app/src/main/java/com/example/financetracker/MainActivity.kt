package com.example.financetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.financetracker.ui.AddTransactionPage
import com.example.financetracker.ui.DateDetailsPage
import com.example.financetracker.ui.DatesListPage
import com.example.financetracker.ui.ProgressSectionPage
import com.example.financetracker.ui.TopSectionPage
import com.example.financetracker.viewmodel.TransactionViewModel
import com.example.financetracker.viewmodel.TransactionViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionViewModel = TransactionViewModelFactory(App.getDao(), App.getAPI()).create(TransactionViewModel::class.java)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Constants.NAVIGATION_DATES_LIST) {
                // List of recorded financial data (dates with transactions)
                composable(Constants.NAVIGATION_DATES_LIST) {
                    DatesListPage(
                        navController,
                        transactionViewModel
                    )
                }

                // List of transactions from a specific date
                composable(
                    Constants.NAVIGATION_DATE_DETAILS,
                    arguments = listOf(navArgument(Constants.NAVIGATION_ARGUMENT) {
                        type = NavType.StringType
                    })
                ) {backStackEntry ->
                    backStackEntry.arguments?.getString(Constants.NAVIGATION_ARGUMENT)?.let {
                        DateDetailsPage(
                            transactionViewModel = transactionViewModel
                        )
                    }
                }

                // Add Transaction
                composable(Constants.NAVIGATION_CREATE_TRANSACTION) {
                    AddTransactionPage(
                        navController,
                        transactionViewModel
                    )
                }

                // Top Section
                composable(Constants.NAVIGATION_TOP_SECTION) {
                    TopSectionPage(
                        transactionViewModel
                    )
                }

                // Progress Section
                composable(Constants.NAVIGATION_PROGRESS_SECTION) {
                    ProgressSectionPage(
                        transactionViewModel
                    )
                }
            }
        }
    }
}