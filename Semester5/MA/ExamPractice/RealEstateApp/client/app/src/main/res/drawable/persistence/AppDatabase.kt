package com.example.realestateapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lab_2.persistence.AppDao
import com.example.realestateapp.model.Property

@Database(entities = [Property::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun AppDao(): AppDao
}