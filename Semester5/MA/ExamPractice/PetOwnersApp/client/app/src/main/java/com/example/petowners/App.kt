package com.example.petowners

import android.app.Application
import androidx.room.Room
import com.example.petowners.Constants.SERVER_IP
import com.example.petowners.Constants.SERVER_PORT
import com.example.petowners.persistence.AppDao
import com.example.petowners.persistence.AppDatabase
import com.example.petowners.server.ApiService
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
                AppDatabase::class.java, Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
        return db!!
    }

    companion object {
        private var instance: App? = null

        fun getDao(): AppDao {
            return instance!!.getDb().AppDao()
        }

        fun getApi(): ApiService {
            return instance!!.apiService
        }

        private fun initRetrofit() {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://${SERVER_IP}:${SERVER_PORT}")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            instance!!.apiService = retrofit.create(ApiService::class.java)
        }
    }
}