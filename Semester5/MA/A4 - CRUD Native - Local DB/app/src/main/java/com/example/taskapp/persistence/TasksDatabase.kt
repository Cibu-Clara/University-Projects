package com.example.taskapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskapp.model.Task

@Database(version = 1, entities = [Task::class])
abstract class TasksDatabase : RoomDatabase() {
    abstract fun TasksDao() : TasksDao
}