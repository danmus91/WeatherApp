package com.smollinedo.weatherapp.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.smollinedo.weatherapp.databinding.ActivityMainBinding
import com.smollinedo.weatherapp.viewModel.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val weatherviewModel : WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        weatherviewModel.quoteModel.observe(this, Observer { currentWeather ->
            binding.tvQuote.text = currentWeather.city
            binding.tvAuthor.text = currentWeather.humidity.toString()})

        binding.viewContainer.setOnClickListener{weatherviewModel.randomWeather()}

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}