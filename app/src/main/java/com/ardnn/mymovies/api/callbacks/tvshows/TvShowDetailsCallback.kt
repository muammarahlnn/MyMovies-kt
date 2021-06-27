package com.ardnn.mymovies.api.callbacks.tvshows

import com.ardnn.mymovies.models.TvShow

interface TvShowDetailsCallback {
    fun onSuccess(tvShow: TvShow)
    fun onFailure(message: String)
}