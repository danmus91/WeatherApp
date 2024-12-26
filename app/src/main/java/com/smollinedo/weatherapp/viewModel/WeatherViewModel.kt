package com.smollinedo.weatherapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smollinedo.weatherapp.model.WeatherModel
import com.smollinedo.weatherapp.model.WeatherProvider

class WeatherViewModel : ViewModel() {

    val quoteModel = MutableLiveData<WeatherModel>()

    fun randomWeather(){
        val currentWeather : WeatherModel = WeatherProvider.random()
        quoteModel.postValue(currentWeather)
    }
}