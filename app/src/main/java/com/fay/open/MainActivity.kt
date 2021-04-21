package com.fay.open

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.fay.open.`interface`.weatherService
import com.fay.open.weatherModel.weatherResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var weatherIcon : String = ""

    companion object {
        // API from OpenWeather
        var BaseUrl = "http://api.openweathermap.org/"
        var weatherURL = "http://openweathermap.org/img/wn/"
        var API: String = "f86831358376e872095bdd8365e68faf"
        var units = "metric"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getCurrentData()
    }
    private suspend fun getResult() {

    }


    internal fun getCurrentData() {
        val lon = intent.getDoubleExtra("longitude", 0.00)
        val lat = intent.getDoubleExtra("latitude", 0.00)
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(weatherService::class.java)
        val call = service.getCurrentWeatherData(lat, lon, units, API)
        call.enqueue(object : Callback<weatherResponse> {
            override fun onResponse(call: Call<weatherResponse>, response: Response<weatherResponse>) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!

                    val main = weatherResponse.main
                    val sys = weatherResponse.sys
                    val wind = weatherResponse.wind
                    val weather = weatherResponse.weather
                    val updatedAt = weatherResponse.dt

                    val updatedAtText = "Updated at : "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt * 1000))
                    val temp = main!!.temp + "°C"
                    val tempMin = "Min Temp : " + main.temp_min + "°C"
                    val tempMax = "Max Temp : " + main.temp_max + "°C"
                    val pressure = main.pressure
                    val humidity = main.humidity

                    val sunrise = sys!!.sunrise
                    val sunset = sys.sunset
                    val windSpeed = wind!!.speed
                    val weatherIcon = weather[0].icon
                    val weatherDesc = weather[0].description

                    val address = weatherResponse.name + ", "+ sys.country



                    findViewById<TextView>(R.id.address).text = address
                    findViewById<TextView>(R.id.updated_at).text = updatedAtText
                    findViewById<TextView>(R.id.status).text = weatherDesc!!.capitalize()
                    findViewById<TextView>(R.id.temp).text = temp
                    findViewById<TextView>(R.id.temp_min).text = tempMin
                    findViewById<TextView>(R.id.temp_max).text = tempMax
                    findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                    findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
                    findViewById<TextView>(R.id.wind).text = windSpeed
                    findViewById<TextView>(R.id.pressure).text = pressure
                    findViewById<TextView>(R.id.humidity).text = humidity

                    // Glider for Weather Icon
                    Glide.with(this@MainActivity)
                            .load("$weatherURL$weatherIcon@2x.png")
                            .fitCenter()
                            .into(descIcon)
                    Log.v("1128", "weather icon is $weatherURL$weatherIcon")

                    /* Views populated, Hiding the loader, Showing the main design */
                    findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                    findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

                }
            }

            override fun onFailure(call: Call<weatherResponse>, t: Throwable) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }
        })
    }
}