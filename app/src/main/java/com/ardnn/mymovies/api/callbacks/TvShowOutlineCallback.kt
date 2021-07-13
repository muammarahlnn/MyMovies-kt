package com.ardnn.mymovies.api.callbacks

import com.ardnn.mymovies.models.TvShowOutline

interface TvShowOutlineCallback {
    fun onSuccess(tvShowOutlineList: MutableList<TvShowOutline>)
    fun onFailure(message: String)
}