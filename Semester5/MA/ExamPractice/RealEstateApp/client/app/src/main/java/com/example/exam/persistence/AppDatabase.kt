package com.example.exam.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exam.model.Property

@Database(entities = [Property::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun AppDao(): AppDao
}