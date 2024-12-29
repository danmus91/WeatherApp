package com.smollinedo.weatherapp.model

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val name: String // City name
)

data class Main(
    val temp: Double, // Temperature
    val humidity: Int // Humidity
)

data class Weather(
    val description: String, // Weather description
    val icon: String // Weather icon
)
