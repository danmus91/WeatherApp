package com.smollinedo.weatherapp.view

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
            Toast.makeText(this, "Coordinates: ${latLng.latitude}, ${latLng.longitude}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupAutocomplete() {
        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocompleteFragment) as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Move camera to selected place
                val latLng = place.latLng;

                place.latLng?.let {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
                    googleMap.addMarker(MarkerOptions().position(it).title("Initial Address"))
                }

                // crea bottomSheet
                val bottomSheet = LocationDetailsBottomSheet()
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                Handler(Looper.getMainLooper()).postDelayed({
                    bottomSheet.setLocationDetails(
                        place.name,
                        "Lat: ${latLng.latitude}, Lng: ${latLng.longitude}"
                    )
                }, 100)

            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                Toast.makeText(this@MainActivity, "No se pudo cargar la ubicación. \n Error: $status ", Toast.LENGTH_LONG).show()
            }
        })
    }



}


