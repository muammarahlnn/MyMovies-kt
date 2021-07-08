package com.ardnn.mymovies.api.callbacks.tvshows

import com.ardnn.mymovies.models.TvShowOutline

interface SimilarTvShowsCallback {
    fun onSuccess(similarList: MutableList<TvShowOutline>)
    fun onFailure(message: String)
}