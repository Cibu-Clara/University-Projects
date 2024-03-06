package com.example.financetracker.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.financetracker.model.Transaction

@Dao
interface TransactionDao {

    @Query("select * from transactions")
    fun getTransactions() : List<Transaction>

    @Query("select * from transactions where transactions.id = :id")
    suspend fun getTransactionById(id: Int) : Transaction?

    @Query("select * from transactions where transactions.date = :date")
    suspend fun getTransactionsByDate(date: String) : List<Transaction>

    @Query("select date from transactions")
    fun getDates(): List<String>

    @Insert
    fun insertTransaction(t: Transaction)

    @Update
    fun updateTransaction(t: Transaction)

    @Delete
    fun deleteTransaction(t: Transaction)

    @Query("delete from transactions")
    fun deleteAllTransactions()
}