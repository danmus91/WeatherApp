package com.smollinedo.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("lat"             ) var lat            : Double?  = null,
    @SerializedName("lon"             ) var lon            : Double?  = null,
    @SerializedName("timezone"        ) var timezone       : String?  = null,
    @SerializedName("timezone_offset" ) var timezoneOffset : Int?     = null,
    @SerializedName("current"         ) var current        : Current? = Current()
)

data class Current(
    @SerializedName("dt"         ) var dt         : Int?               = null,
    @SerializedName("sunrise"    ) var sunrise    : Int?               = null,
    @SerializedName("sunset"     ) var sunset     : Int?               = null,
    @SerializedName("temp"       ) var temp       : Double?            = null,
    @SerializedName("feels_like" ) var feelsLike  : Double?            = null,
    @SerializedName("pressure"   ) var pressure   : Int?               = null,
    @SerializedName("humidity"   ) var humidity   : Int?               = null,
    @SerializedName("dew_point"  ) var dewPoint   : Double?            = null,
    @SerializedName("uvi"        ) var uvi        : Double?            = null,
    @SerializedName("clouds"     ) var clouds     : Int?               = null,
    @SerializedName("visibility" ) var visibility : Int?               = null,
    @SerializedName("wind_speed" ) var windSpeed  : Double?            = null,
    @SerializedName("wind_deg"   ) var windDeg    : Int?               = null,
    @SerializedName("wind_gust"  ) var windGust   : Double?            = null,
    @SerializedName("weather"    ) var weather    : ArrayList<Weather> = arrayListOf()
)

data class Weather(
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("main"        ) var main        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("icon"        ) var icon        : String? = null
)