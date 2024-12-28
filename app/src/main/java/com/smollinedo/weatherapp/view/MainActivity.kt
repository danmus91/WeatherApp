package com.smollinedo.weatherapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.smollinedo.weatherapp.R

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createFragment()
    }

    private fun createFragment() {
        val mapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.viewContainer) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap : GoogleMap) {
        map = googleMap
        createMarker()
    }

    private fun createMarker() {
        val coordinates = LatLng(-16.498270, -68.133558)
        val marker : MarkerOptions = MarkerOptions().position(coordinates).title("Mi playa favorita")
        map.addMarker(marker)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 18f),4000,null)

        map.setOnMapClickListener { latLng ->
            // Display the coordinates
            val latitude = latLng.latitude
            val longitude = latLng.longitude
            println("Coordinates: Lat = $latitude, Lng = $longitude")

            // Optionally add a marker on the clicked position
            map.clear() // Clear previous markers
            map.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
        }
    }
}


