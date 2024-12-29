package com.smollinedo.weatherapp.view

import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.smollinedo.weatherapp.R
import com.smollinedo.weatherapp.databinding.ActivityMainBinding
import com.smollinedo.weatherapp.model.APIWeatherService
import com.smollinedo.weatherapp.model.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.Locale


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var googleMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  inicia places API
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_maps_key))
        }

        // inicia map
        val mapFragment = supportFragmentManager.findFragmentById(binding.mapFragment.id)as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupAutocomplete()
    }

    override fun onMapReady(map : GoogleMap) {
        googleMap = map

        // Default location
        val defaultLocation = LatLng(-16.4983148, -68.1335649) //
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 20f))

        // Add a map click listener
        googleMap.setOnMapClickListener { latLng ->
            googleMap.clear() // Clear previous markers
            googleMap.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
        //    Toast.makeText(this, "Coordinates: ${latLng.latitude}, ${latLng.longitude}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCityAndCountry(latLng: LatLng): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        return if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0]  // Obtener la primera dirección
            val thoroughfare = address.thoroughfare ?: "Calle desconocida" // Nombre de la calle
            val subThoroughfare = address.subThoroughfare ?: "" // Número de la calle o edificio
            val locality = address.locality ?: "Localidad desconocida" // Ciudad o localidad
            val adminArea = address.adminArea ?: "Región desconocida" // Región o estado
            val country = address.countryName ?: "País desconocido" // País

            // Construir la dirección completa
            "$thoroughfare $subThoroughfare, $locality, $adminArea, $country"
        } else {
            "Ubicación no disponible"
        }
    }

    private fun setupAutocomplete() {
        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocompleteFragment) as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Move camera to selected place
                val latLng = place.location;
                val name = place.name ?: "Ubicación desconocida"
                val coordinates = "Lat: ${latLng?.latitude}, Lng: ${latLng?.longitude}"

                place.latLng?.let {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
                    googleMap.addMarker(MarkerOptions().position(it).title("Initial Address"))
                }
                val locationDetails = getCityAndCountry(latLng)

                // crea bottomSheet
                val bottomSheet = LocationDetailsBottomSheet()
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                Handler(Looper.getMainLooper()).postDelayed({
                    getWeatherAPIFromAPI(latLng.latitude, latLng.longitude, bottomSheet, name, coordinates)
                }, 100)

            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                Toast.makeText(this@MainActivity, "No se pudo cargar la ubicación. \n Error: $status ", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/3.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getWeatherAPIFromAPI(lat: Double, lon: Double, bottomSheet: LocationDetailsBottomSheet, name: String, coordinates: String)
    {
        CoroutineScope(Dispatchers.IO).launch {
            val call : Response<WeatherResponse> = getRetrofit()
                .create(APIWeatherService::class.java)
                .getWeather("onecall?exclude=hourly,daily,minutely&lat=$lat&lon=$lon&units=metric&appid=ba7d440082bec37e5c4baa7a9fb757a5")
            val weather : WeatherResponse? = call.body()

            if (call.isSuccessful && weather != null) {
                val temp = "${weather.current?.temp}°C"
                val description = weather.current?.weather?.get(0)?.description

                runOnUiThread {
                    // Pasar datos al BottomSheet
                    bottomSheet.setLocationDetails(name, coordinates, description?:"no info", temp)
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error al obtener el clima: ${call.message()}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}


