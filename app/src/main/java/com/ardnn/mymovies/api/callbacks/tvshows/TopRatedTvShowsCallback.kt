package com.ardnn.mymovies.api.callbacks.tvshows

import com.ardnn.mymovies.models.TvShowOutline

interface TopRatedTvShowsCallback {
    fun onSuccess(topRatedTvShowsList: List<TvShowOutline>)
    fun onFailure(message: String)
}