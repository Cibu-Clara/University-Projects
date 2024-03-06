package com.example.petowners.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.petowners.model.Pet

@Database(entities = [Pet::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun AppDao(): AppDao
}