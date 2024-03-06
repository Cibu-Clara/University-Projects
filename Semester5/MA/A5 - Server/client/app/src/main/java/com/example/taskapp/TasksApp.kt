package com.example.taskapp

import android.app.Application
import androidx.room.Room
import com.example.taskapp.data.TaskAPI
import com.example.taskapp.data.persistence.TasksDao
import com.example.taskapp.data.persistence.TasksDatabase
import com.example.taskapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TasksApp : Application() {
    private var db: TasksDatabase? = null
    private lateinit var taskAPI: TaskAPI
    private var isApiInitialized = false

    init {
        instance = this
        initRetrofit()
    }

    private fun getDatabase(): TasksDatabase {
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

        fun getDao(): TasksDao {
            return instance!!.getDatabase().TasksDao()
        }

        fun getAPI(): TaskAPI {
            return instance!!.taskAPI
        }

        private fun initRetrofit() {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://172.30.113.46:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            instance!!.taskAPI = retrofit.create(TaskAPI::class.java)
            instance!!.isApiInitialized = true
        }
    }
}
