package com.example.petowners.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.petowners.Constants

@Entity(tableName = Constants.TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class Pet(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "breed") val breed: String? = null,
    @ColumnInfo(name = "age") val age: Int? = null,
    @ColumnInfo(name = "weight") val weight: Int? = null,
    @ColumnInfo(name = "owner") val owner: String? = null,
    @ColumnInfo(name = "location") val location: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
) {
    override fun toString(): String {
        return "$name $breed"
    }
}
