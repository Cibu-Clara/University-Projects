package com.example.financetracker.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.viewmodel.TransactionViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProgressSectionPage(
    transactionViewModel: TransactionViewModel
) {
    val weeklyTotals = remember { mutableStateOf<Map<String, Double>>(emptyMap()) }

    LaunchedEffect(true) {
        val totals = transactionViewModel.getWeeklyTotals()
        weeklyTotals.value = totals
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Column(
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                ) {
                    Text(
                        text = "Weekly Totals",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            ) {
                weeklyTotals.value.forEach { (week, totalAmount) ->
                    Text(
                        text = "$week: $totalAmount",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}
