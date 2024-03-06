package com.example.realestateapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "properties", indices = [Index(value = ["id"], unique = true)])
data class Property(
    @PrimaryKey() val id :Int,

    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "type") val type: String = "",
    @ColumnInfo(name = "address") val address: String = "",
    @ColumnInfo(name = "bedrooms") val bedrooms: Int,
    @ColumnInfo(name = "bathrooms") val bathrooms: Int,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "area") val area: Int,
    @ColumnInfo(name = "notes") val notes: String = "",
)