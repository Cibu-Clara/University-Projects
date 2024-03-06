package com.example.petowners.server

import com.example.petowners.model.Pet
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/pets")
    fun getPets(): Call<List<Pet>>

    @GET("/pet/{id}")
    fun getPetById(@Path("id") id: Int): Call<Pet>

    @POST("/pet")
    fun createPet(@Body pet: Pet): Call<Pet>

    @DELETE("pet/{id}")
    fun deletePet(@Path("id") id: Int): Call<Void>

    @GET("/search")
    fun searchPets(): Call<List<Pet>>
}