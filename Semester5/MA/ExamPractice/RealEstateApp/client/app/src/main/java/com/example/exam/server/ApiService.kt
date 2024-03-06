package com.example.exam.server

import com.example.exam.model.Property
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/properties/")
    fun getEntities(): Call<List<Property>>

    @GET("/search/")
    fun searchEntities(): Call<List<Property>>

    @GET("/property/{id}/")
    fun getEntity(@Path("id") id: Int): Call<Property>

    @POST("/property/")
    fun createEntity(@Body entity: Property): Call<Property>

    @PUT("/property/{id}/")
    fun updateEntity(@Path("id") id: Int, @Body entity: Property): Call<Property>

    @DELETE("/property/{id}/")
    fun deleteEntity(@Path("id") id: Int): Call<Void>
}