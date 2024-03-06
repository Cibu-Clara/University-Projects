package com.example.taskapp.data.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskapp.data.model.Task

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks WHERE tasks.id=:id")
    fun getTaskById(id: Int): Task?

    @Query("SELECT * FROM tasks")
    fun getTasks(): List<Task>

    @Insert
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Insert
    fun insertTasks(vararg tasks: Task)

    @Delete
    fun deleteTasks(vararg tasks: Task)

    @Update
    fun updateTasks(vararg tasks: Task)

    @Query("DELETE FROM tasks")
    fun deleteAllTasks()
}