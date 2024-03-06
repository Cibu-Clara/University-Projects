package com.example.taskapp

import android.app.Application
import androidx.room.Room
import com.example.taskapp.persistence.TasksDao
import com.example.taskapp.persistence.TasksDatabase

class TasksApp : Application() {
    private var db : TasksDatabase? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private fun getDatabase() : TasksDatabase {
        if (db == null) {
            db = Room.databaseBuilder(
                instance!!.applicationContext,
                TasksDatabase::class.java, Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
        return db!!
    }

    companion object {
        private var instance: TasksApp? = null

        fun getDao() : TasksDao {
            return instance!!.getDatabase().TasksDao()
        }
    }
}