package com.example.taskapp.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskapp.data.model.Task

@Database(version = 1, entities = [Task::class])
abstract class TasksDatabase : RoomDatabase() {
    abstract fun TasksDao() : TasksDao
}