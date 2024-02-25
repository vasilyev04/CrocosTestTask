package com.vasilyev.crocostesttask.data.datasource.remote.retrofit

import com.vasilyev.crocostesttask.BuildConfig
import com.vasilyev.crocostesttask.data.entity.route.DirectionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/maps/api/directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String = API_KEY
    ): Response<DirectionResponse>


    companion object{
        private const val API_KEY = BuildConfig.MAPS_API_KEY
    }
}