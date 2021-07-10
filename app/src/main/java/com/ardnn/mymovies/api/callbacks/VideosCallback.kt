package com.ardnn.mymovies.api.callbacks

import com.ardnn.mymovies.models.Video

interface VideosCallback {
    fun onSuccess(videoList: List<Video>)
    fun onFailure(message: String)
}