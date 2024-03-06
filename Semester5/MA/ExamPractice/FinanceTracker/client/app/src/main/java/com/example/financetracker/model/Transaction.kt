package com.example.financetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.financetracker.Constants

@Entity(tableName = Constants.TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class Transaction(
    @PrimaryKey val id :Int,

    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "type") val type: String? = null,
    @ColumnInfo(name = "amount") val amount: Double? = null,
    @ColumnInfo(name = "category") val category: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
) {
    override fun toString(): String {
        return "$date $amount"
    }
}
