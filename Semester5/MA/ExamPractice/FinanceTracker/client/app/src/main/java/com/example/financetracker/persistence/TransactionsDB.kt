package com.example.financetracker.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.financetracker.model.Transaction

@Database(entities = [Transaction::class], version = 2, exportSchema = false)
abstract class TransactionsDB : RoomDatabase(){
    abstract fun TransactionDao(): TransactionDao
}