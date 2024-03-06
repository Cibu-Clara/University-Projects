package com.example.taskapp.data

import com.example.taskapp.data.model.Task
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TaskAPI {

    @GET("/tasks")
    fun getTasks() : Call<List<Task>>

    @GET("/tasks/details")
    fun getTask(@Query("id") id: Int) : Call<Task>

    @POST("/tasks/create")
    fun createTask(@Body task: Task) : Call<Task>

    @PUT("/tasks/update")
    fun updateTask(@Body task: Task) : Call<Task>

    @DELETE("/tasks/delete")
    fun deleteTask(@Query("id") id: Int) : Call<Void>
}