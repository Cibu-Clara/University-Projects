package com.example.financetracker.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
fun TopSectionPage(
    transactionViewModel: TransactionViewModel
) {
    val topCategories = remember{ mutableStateOf<List<Pair<String, Int>>>(emptyList()) }

    LaunchedEffect(true) {
        val categories = transactionViewModel.getTopCategories()
        topCategories.value = categories as List<Pair<String, Int>>
    }

    Surface (
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Scaffold (
            topBar = {
                Column(
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                ) {
                    Text(
                        text = "Top 3 Categories",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
            }
        ){
            Column(
                modifier = Modifier.padding(start= 20.dp, top = 20.dp)
            )  {
                topCategories.value.forEachIndexed { index, (category, count) ->
                    Text(
                        text = "${index + 1}. $category - $count transactions",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }

}