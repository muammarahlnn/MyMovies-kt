package com.ardnn.mymovies.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ardnn.mymovies.databinding.ActivityVideoPlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideoPlayerActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_VIDEO_KEY = "extra_video_key"
    }

    // view binding
    private lateinit var binding: ActivityVideoPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialization
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoKey: String = intent.getStringExtra(EXTRA_VIDEO_KEY).toString()
                youTubePlayer.cueVideo(videoKey, 0F)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()

        // release youtube player view when we're done using it
        binding.youtubePlayerView.release()
    }
}