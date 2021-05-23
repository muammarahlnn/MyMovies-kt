package com.ardnn.mymovies.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.helpers.Animation

class SplashActivity : AppCompatActivity() {
    // classes
    private lateinit var animation: Animation

    // widgets
    private lateinit var tvTitle: TextView
    private lateinit var ivMovies: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // initialization
        animation = Animation(this)
        ivMovies = findViewById(R.id.iv_movies_splash)
        tvTitle = findViewById(R.id.tv_title_splash)

        // set animation to widgets
        ivMovies.animation = animation.fadeIn
        tvTitle.animation = animation.fadeIn

        // set timer for 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val goToMain = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(goToMain)
            finish()
        }, 1800)
    }
}