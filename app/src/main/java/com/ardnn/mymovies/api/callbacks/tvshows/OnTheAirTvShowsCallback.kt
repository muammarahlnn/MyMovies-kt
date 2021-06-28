package com.ardnn.mymovies.api.callbacks.tvshows

import com.ardnn.mymovies.models.TvShowOutline

interface OnTheAirTvShowsCallback {
    fun onSuccess(onTheAirList: MutableList<TvShowOutline>)
    fun onFailure(message: String)
}