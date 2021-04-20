package com.fay.open.`interface`

import com.fay.open.weatherModel.weatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface weatherService {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") metric: String,
        @Query("APPID") app_id: String): Call<weatherResponse>

}