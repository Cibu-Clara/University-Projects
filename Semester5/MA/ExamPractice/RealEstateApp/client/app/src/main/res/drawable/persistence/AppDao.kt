package com.example.realestateapp.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.UUID

@Dao
interface AppDao {

    @Query("select * from regular_users where regular_users.uuid = :uuid")
    suspend fun getRegularUserByUUID(uuid: UUID) : RegularUser?

    @Query("select * from regular_users")
    fun getRegularUsers() : List<RegularUser>

    @Delete
    fun deleteRegularUser(regularUser: RegularUser)

    @Update
    fun updateRegularUser(regularUser: RegularUser)

    @Insert
    fun insertRegularUser(regularUser: RegularUser)

    @Query("DELETE FROM regular_users")
    fun deleteAllRegularUsers()

}