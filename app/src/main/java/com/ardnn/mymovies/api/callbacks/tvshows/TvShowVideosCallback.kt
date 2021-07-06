package com.ardnn.mymovies.api.callbacks.tvshows

import com.ardnn.mymovies.models.Video

interface TvShowVideosCallback {
    fun onSuccess(videoList: MutableList<Video>)
    fun onFailure(message: String)
}