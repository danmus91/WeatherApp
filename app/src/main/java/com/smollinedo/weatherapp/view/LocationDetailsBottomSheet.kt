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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño del bottom sheet
        val view = inflater.inflate(R.layout.bottom_sheet_weather, container, false)
        locationName = view.findViewById(R.id.location_name)
        locationCoordinates = view.findViewById(R.id.location_coordinates)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa las vistas
        locationName = view.findViewById(R.id.location_name)
        locationCoordinates = view.findViewById(R.id.location_coordinates)
    }

    fun setLocationDetails(name: String, coordinates: String) {

        Log.d("LocationDetails", "Location Name: $name")
        Log.d("LocationDetails", "Coordinates: $coordinates")

        if (locationName != null && locationCoordinates != null) {
            locationName?.text = name
            locationCoordinates?.text = coordinates
        } else {
            // Si las vistas no se han inicializado correctamente, se vuelve a intentar
            // Esto puede ocurrir si se llama a setLocationDetails antes de que el fragmento se haya creado.
            // Puedes agregar un log para verificar si está ocurriendo esto
            println("Vistas aún no inicializadas. Intentando más tarde.")
        }
    }
}