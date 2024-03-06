package com.example.exam.server

import com.example.exam.model.Property
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PropertyAPI {
    @GET("/listings")
    fun getProperties(): Call<List<Property>>

    @GET("/property/{id}/")
    fun getPropertyById(@Path("id") id: Int): Call<Property>

    @GET("/types")
    fun getTypes(): Call<List<String>>

    @POST("/property")
    fun createProperty(@Body property: Property): Call<Property>
}