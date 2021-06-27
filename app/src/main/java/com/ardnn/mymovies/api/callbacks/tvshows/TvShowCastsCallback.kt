package com.ardnn.mymovies.api.callbacks.tvshows

import com.ardnn.mymovies.models.Cast

interface TvShowCastsCallback {
    fun onSuccess(castList: List<Cast>)
    fun onFailure(message: String)
}