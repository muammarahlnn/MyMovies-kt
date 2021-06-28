package com.ardnn.mymovies.api.callbacks.movies

import com.ardnn.mymovies.models.MovieOutline

interface UpcomingMoviesCallback {
    fun onSuccess(upcomingList: MutableList<MovieOutline>)
    fun onFailure(message: String)
}