package com.fay.open

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Retrofit

class PermissionActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        val desc = findViewById<TextView>(R.id.tv_desc)

        desc.text =
                "Hey, " +
                        "I need access to your device for this weather app. "

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        findViewById<Button>(R.id.btn_get).setOnClickListener {
            fetchLocation()
        }

    }

    // Permission to get location
    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                        .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        // When access allowed, pass the location (latitude and longitude to another activity)
        task.addOnSuccessListener {
            if (it != null) {
                val intent = Intent(this@PermissionActivity,
                        MainActivity::class.java)
                        .putExtra("latitude", it.latitude)
                        .putExtra("longitude", it.longitude)
                startActivity(intent)
                finish()
            }
        }
    }
}