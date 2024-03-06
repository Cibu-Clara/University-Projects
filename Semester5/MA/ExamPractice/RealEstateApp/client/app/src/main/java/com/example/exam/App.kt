package com.example.exam

import android.app.Application
import androidx.room.Room
import com.example.exam.persistence.AppDao
import com.example.exam.persistence.AppDatabase
import com.example.exam.server.ApiService
import com.example.exam.Constants.SERVER_IP
import com.example.exam.Constants.SERVER_PORT

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    private var db: AppDatabase? = null
    private lateinit var apiService: ApiService

    init {
        instance = this
        initRetrofit()
    }

    private fun getDb(): AppDatabase {
        if (db != null) {
            return db!!
        } else {
            db = Room.databaseBuilder(
                instance!!.applicationContext,
                AppDatabase::class.java, "Database"
            ).fallbackToDestructiveMigration().build()
        }
        return db!!
    }

    companion object {
        private var instance: App? = null

        fun getDao(): AppDao {
            return instance!!.getDb().AppDao()
        }

        fun getAPI(): ApiService {
            return instance!!.apiService
        }

        private fun initRetrofit() {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://${SERVER_IP}:${SERVER_PORT}/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            instance!!.apiService = retrofit.create(ApiService::class.java)
        }
    }
}

