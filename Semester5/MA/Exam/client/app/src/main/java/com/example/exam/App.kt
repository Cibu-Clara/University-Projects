package com.example.exam

import android.app.Application
import androidx.room.Room
import com.example.exam.persistence.PropertyDB
import com.example.exam.persistence.PropertyDao
import com.example.exam.server.PropertyAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    private var db: PropertyDB? = null
    private lateinit var propertyAPI: PropertyAPI

    init {
        instance = this
        initRetrofit()
    }

    private fun getDb(): PropertyDB {
        if (db != null) {
            return db!!
        } else {
            db = Room.databaseBuilder(
                instance!!.applicationContext,
                PropertyDB::class.java, Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
        return db!!
    }

    companion object {
        private var instance: App? = null

        fun getDao(): PropertyDao {
            return instance!!.getDb().PropertyDao()
        }

        fun getAPI(): PropertyAPI {
            return instance!!.propertyAPI
        }

        private fun initRetrofit() {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://${Constants.SERVER_IP}:${Constants.SERVER_PORT}/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            instance!!.propertyAPI = retrofit.create(PropertyAPI::class.java)
        }
    }
}