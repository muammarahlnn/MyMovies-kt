package com.ardnn.mymovies.api.callbacks.tvshows

import com.ardnn.mymovies.models.TvShowOutline

interface PopularTvShowsCallback {
    fun onSuccess(popularTvShowsList: MutableList<TvShowOutline>)
    fun onFailure(message: String)
}