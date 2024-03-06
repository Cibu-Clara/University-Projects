package com.example.exam.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "properties", indices = [Index(value = ["id"], unique = true)])
data class Property(
    @PrimaryKey() val id :Int,

    @ColumnInfo(name = "date") val date: String? = null,
    @ColumnInfo(name = "type") val type: String? = null,
    @ColumnInfo(name = "address") val address: String = "",
    @ColumnInfo(name = "bedrooms") val bedrooms: Int? = null,
    @ColumnInfo(name = "bathrooms") val bathrooms: Int? = null,
    @ColumnInfo(name = "price") val price: Double? = null,
    @ColumnInfo(name = "area") val area: Int? = null,
    @ColumnInfo(name = "notes") val notes: String? = null,
){
    override fun toString(): String {
        return "$address $type $price"
    }
}