package com.example.exam.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.exam.model.Property

@Dao
interface AppDao {

    @Query("select * from properties where properties.id = :id")
    suspend fun getEntityById(id: Int) : Property?

    @Query("select * from properties")
    fun getEntities() : List<Property>

    @Delete
    fun deleteEntity(entity: Property)

    @Update
    fun updateEntity(entity: Property)

    @Insert
    fun insertEntity(entity: Property)

    @Query("DELETE FROM properties")
    fun deleteAllEntities()

}