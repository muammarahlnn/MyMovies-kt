package com.ardnn.mymovies.api.callbacks.movies

import com.ardnn.mymovies.models.Video

interface MovieVideosCallback {
    fun onSuccess(videoList: MutableList<Video>)
    fun onFailure(message: String)
}