package com.example.uts_biodata_pribadi

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    fun getAll(): Call<List<Biodata>>

    @POST("users")
    fun insert(@Body data: Biodata): Call<Biodata>

    @PUT("users/{id}")
    fun update(@Path("id") id: Int, @Body data: Biodata): Call<Biodata>

    @DELETE("users/{id}")
    fun delete(@Path("id") id: Int): Call<Void>
}

