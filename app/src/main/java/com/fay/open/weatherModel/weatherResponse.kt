package com.fay.open.weatherModel

import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class weatherResponse {


    @SerializedName("main")
    var main: Main? = null
    @SerializedName("sys")
    var sys: Sys ?= null
    @SerializedName("wind")
    var wind: Wind ?= null
    @SerializedName("weather")
    var weather = ArrayList<Weather>()
    @SerializedName("dt")
    var dt: Long = 0L
    @SerializedName("name")
    var name: String = ""
}

class Main {
    @SerializedName("temp")
    var temp: String = ""
    @SerializedName("humidity")
    var humidity: String = ""
    @SerializedName("pressure")
    var pressure: String = ""
    @SerializedName("temp_min")
    var temp_min: Double = 0.toDouble()
    @SerializedName("temp_max")
    var temp_max: Double = 0.toDouble()
}

class Sys {
    @SerializedName("country")
    var country: String? = null
    @SerializedName("sunrise")
    var sunrise: Long = 0
    @SerializedName("sunset")
    var sunset: Long = 0
}

class Weather {
    @SerializedName("description")
    var description: String ?= null
    @SerializedName("icon")
    var icon: String ?= null
}

class Wind {
    @SerializedName("speed")
    var speed: String ?= null
}

