package com.smollinedo.weatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smollinedo.weatherapp.R

class LocationDetailsBottomSheet : BottomSheetDialogFragment()  {

    private var locationName: TextView? = null
    private var locationCoordinates: TextView? = null
    private var weatherDescription: TextView? = null
    private var temperature: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño del bottom sheet
        val view = inflater.inflate(R.layout.bottom_sheet_weather, container, false)
        locationName = view.findViewById(R.id.location_name)
        locationCoordinates = view.findViewById(R.id.location_coordinates)
        weatherDescription = view.findViewById(R.id.weather_description)
        temperature = view.findViewById(R.id.temperature)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa las vistas
        locationName = view.findViewById(R.id.location_name)
        locationCoordinates = view.findViewById(R.id.location_coordinates)
    }

    fun setLocationDetails(name: String, coordinates: String, weather: String, temp: String) {

        Log.d("LocationDetails", "Location Name: $name")
        Log.d("LocationDetails", "Coordinates: $coordinates")

        locationName?.text = name
        locationCoordinates?.text = coordinates
        weatherDescription?.text = weather
        temperature?.text = temp

//        if (locationName != null && locationCoordinates != null) {
//            locationName?.text = name
//            locationCoordinates?.text = coordinates
//            weatherDescription?.text = weather
//            temperature?.text = temp
//        } else {
//            println("Vistas aún no inicializadas. Intentando más tarde.")
//        }
    }
}