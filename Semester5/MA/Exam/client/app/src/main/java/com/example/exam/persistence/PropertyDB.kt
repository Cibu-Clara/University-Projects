package com.example.exam.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exam.model.Property

@Database(entities = [Property::class], version = 1, exportSchema = false)
abstract class PropertyDB : RoomDatabase() {
    abstract fun PropertyDao(): PropertyDao
}