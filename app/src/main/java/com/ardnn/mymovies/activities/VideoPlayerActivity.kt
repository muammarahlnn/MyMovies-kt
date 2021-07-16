package com.ardnn.mymovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardnn.mymovies.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoPlayerActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_VIDEO_KEY = "extra_video_key"
    }

    // widgets
    private lateinit var youtubePlayer: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        // initialization
        youtubePlayer = findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youtubePlayer)

        youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoKey: String = intent.getStringExtra(EXTRA_VIDEO_KEY).toString()
                youTubePlayer.cueVideo(videoKey, 0F)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()

        // release youtube player view when we're done using it
        youtubePlayer.release()
    }
}