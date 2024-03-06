package com.example.petowners.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.petowners.model.Pet

@Dao
interface AppDao {

    @Query("select * from pets")
    fun getPets(): List<Pet>

    @Query("select * from pets where pets.id = :id")
    suspend fun getPetById(id: Int) : Pet?

    @Insert
    fun insertPet(pet: Pet)

    @Delete
    fun deletePet(pet: Pet)

    @Update
    fun updatePet(pet: Pet)

    @Query("delete from pets")
    fun deleteAllPets()
}