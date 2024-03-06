package com.example.exam.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.exam.model.Property

@Dao
interface PropertyDao {
    @Query("select * from properties where properties.id = :id")
    suspend fun getPropertyById(id: Int) : Property?

    @Query("select type from properties")
    fun getTypes(): List<String>

    @Query("select * from properties")
    fun getProperties() : List<Property>

    @Insert
    fun insertProperty(entity: Property)

    @Update
    fun updateProperty(entity: Property)

    @Query("DELETE FROM properties")
    fun deleteAllProperties()
}