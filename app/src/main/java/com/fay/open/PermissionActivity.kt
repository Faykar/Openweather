package com.fay.open

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

class PermissionActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient


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

    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                        .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
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