package com.example.financetracker

import android.app.Application
import androidx.room.Room
import com.example.financetracker.Constants.SERVER_IP
import com.example.financetracker.Constants.SERVER_PORT
import com.example.financetracker.persistence.TransactionDao
import com.example.financetracker.persistence.TransactionsDB
import com.example.financetracker.server.TransactionAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    private var db: TransactionsDB? = null
    private lateinit var transactionAPI: TransactionAPI

    init {
        instance = this
        initRetrofit()
    }

    private fun getDb(): TransactionsDB {
        if (db != null) {
            return db!!
        } else {
            db = Room.databaseBuilder(
                instance!!.applicationContext,
                TransactionsDB::class.java, Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
        return db!!
    }

    companion object {
        private var instance: App? = null

        fun getDao(): TransactionDao {
            return instance!!.getDb().TransactionDao()
        }

        fun getAPI(): TransactionAPI {
            return instance!!.transactionAPI
        }

        private fun initRetrofit() {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://${SERVER_IP}:${SERVER_PORT}/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            instance!!.transactionAPI = retrofit.create(TransactionAPI::class.java)
        }
    }
}