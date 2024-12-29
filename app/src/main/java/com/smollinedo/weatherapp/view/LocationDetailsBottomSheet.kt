package com.smollinedo.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smollinedo.weatherapp.R
import com.squareup.picasso.Picasso

class LocationDetailsBottomSheet : BottomSheetDialogFragment()  {

    private var locationName: TextView? = null
    private var locationCoordinates: TextView? = null
    private var weatherDescription: TextView? = null
    private var temperature: TextView? = null
    private var humidity: TextView? = null
    private var weatherIcon: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño del bottom sheet
        val view = inflater.inflate(R.layout.bottom_sheet_weather, container, false)
        locationName = view.findViewById(R.id.city)
        locationCoordinates = view.findViewById(R.id.address)
        weatherDescription = view.findViewById(R.id.weather_description)
        humidity = view.findViewById(R.id.humidity)
        temperature = view.findViewById(R.id.temperature)
        weatherIcon = view.findViewById(R.id.weather_icon)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa las vistas
        locationName = view.findViewById(R.id.city)
        locationCoordinates = view.findViewById(R.id.address)
        weatherDescription = view.findViewById(R.id.weather_description)
        humidity = view.findViewById(R.id.humidity)
        temperature = view.findViewById(R.id.temperature)
    }

    fun setLocationDetails(city: String, address: String, description:String, hummed: String, temp: String) {

        locationName?.text = city
        locationCoordinates?.text = address
        weatherDescription?.text = description
        humidity?.text = hummed
        temperature?.text = temp

    }

    fun setWeatherIcon(iconUrl: String) {
        weatherIcon?.let {
            Picasso.get()
                .load(iconUrl)
                .into(weatherIcon)
        }
    }
}