package com.ardnn.mymovies.api.callbacks.movies

import com.ardnn.mymovies.models.MovieOutline

interface NowPlayingMoviesCallback {
    fun onSuccess(nowPlayingList: MutableList<MovieOutline>)
    fun onFailure(message: String)
}