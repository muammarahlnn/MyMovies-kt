package com.ardnn.mymovies.helpers

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class AppEndedService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AppEnded", "Service started")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AppEnded", "Service destroyed")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.d("AppEnded", "The end")

        // set the rerun flag to false
        Utils.saveRerunFlag(this, false)
        stopSelf()
    }

}