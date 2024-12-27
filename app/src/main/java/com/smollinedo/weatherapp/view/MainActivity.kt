package com.smollinedo.weatherapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.smollinedo.weatherapp.R
import com.smollinedo.weatherapp.databinding.ActivityMainBinding
import com.smollinedo.weatherapp.viewModel.WeatherViewModel

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map : GoogleMap

//    private val weatherviewModel : WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        weatherviewModel.quoteModel.observe(this, Observer { currentWeather ->
//            binding.tvQuote.text = currentWeather.city
//            binding.tvAuthor.text = currentWeather.humidity.toString()})

        //ibnding.viewContainer.setOnClickListener{weatherviewModel.randomWeather()}

        createFragment()


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    private fun createFragment() {
        val mapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.viewContainer) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap : GoogleMap) {
        map = googleMap
        createMarker()
    }

    private fun createMarker(){
        val coordinates = LatLng(28.043893,-16.539329)
        val marker : MarkerOptions = MarkerOptions().position(coordinates).title("Mi playa favorita")
        map.addMarker(marker)
    }
}