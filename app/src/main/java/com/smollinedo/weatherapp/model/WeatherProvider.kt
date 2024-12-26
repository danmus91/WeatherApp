package com.smollinedo.weatherapp.model

class WeatherProvider {

    companion object{

        fun random ():WeatherModel{
            val position : Int = (0..2).random()
            return weather[position]
        }

        private val weather = listOf<WeatherModel>(
            WeatherModel(lat = 22.1, lon = 34.2, pressure = 70, humidity = 30, city = "La Paz"),
            WeatherModel(lat = 23.2, lon = 32.2, pressure = 70, humidity = 30, city = "Santa Cruz"),
            WeatherModel(lat = 2.2, lon = 33.2, pressure = 70, humidity = 30, city = "Tarija"),
            )
    }

}