package com.example.financetracker.server

import com.example.financetracker.model.Transaction
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionAPI {

    @GET("/days")
    fun getDates(): Call<List<String>>

    @GET("/transactions/{date}")
    fun getTransactionsByDate(@Path("date") date: String): Call<List<Transaction>>

    @GET("/entries")
    fun getEntries(): Call<List<Transaction>>

    @POST("/transaction")
    fun createTransaction(@Body t: Transaction): Call<Transaction>

    @DELETE("/transaction/{id}")
    fun deleteTransaction(@Path("id") id: Int): Call<Void>
}