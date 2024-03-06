package com.example.taskapp.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskapp.model.Task

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks WHERE tasks.id=:id")
    suspend fun getTaskById(id: Int): Task?

    @Query("SELECT * FROM tasks")
    fun getTasks(): LiveData<List<Task>>

    @Insert
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)
}