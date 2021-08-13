package com.ardnn.mymovies.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ardnn.mymovies.R
import com.ardnn.mymovies.helpers.AppEndedService
import com.ardnn.mymovies.helpers.Utils

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // start service if app is ended
        val appEndedService = Intent(this@SplashActivity, AppEndedService::class.java)
        startService(appEndedService)

        // check if first run or not
        val isRerun: Boolean = Utils.getRerunFlag(this)
        if (!isRerun) { // first run app

            // set the rerun flag to true
            Utils.saveRerunFlag(this, true)

            // set content
            setContentView(R.layout.activity_splash)

            // go to main
            delayIntentToMain(1200)
        } else { // re run app
            // set content
            setContentView(R.layout.activity_splash_rerun)

            // go to main
            delayIntentToMain(120)
        }

    }

    private fun delayIntentToMain(duration: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            // go to main activity
            val goToMain = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(goToMain)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, duration)
    }
}