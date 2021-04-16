package com.fay.open

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //Declare the Animation
        val ltc = AnimationUtils.loadAnimation(this, R.anim.ltc)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val title = findViewById<TextView>(R.id.tv_title)
        val iv_icon = findViewById<ImageView>(R.id.iv_icon)

        //Set the Animation
        title.startAnimation(ltc)
        iv_icon.startAnimation(stb)


        // Delay for the Splashscreen
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity,
                PermissionActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}