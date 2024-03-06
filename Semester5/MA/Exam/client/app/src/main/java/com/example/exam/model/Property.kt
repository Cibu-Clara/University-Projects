package com.example.exam.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.exam.Constants

@Entity(tableName = Constants.TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class Property(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "date") val date: String? = null,
    @ColumnInfo(name = "details") val details: String? = null,
    @ColumnInfo(name = "status") val status: String? = null,
    @ColumnInfo(name = "viewers") val viewers: Int? = null,
    @ColumnInfo(name = "type") val type: String? = null,
) {
    override fun toString(): String {
        return name
    }
}
