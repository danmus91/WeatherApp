package com.smollinedo.weatherapp.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIWeatherService {
    @GET
    suspend fun getWeather(@Url url:String): Response<WeatherResponse>
}