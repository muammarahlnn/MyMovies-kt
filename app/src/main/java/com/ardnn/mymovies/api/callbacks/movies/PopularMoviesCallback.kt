package com.ardnn.mymovies.api.callbacks.movies

import com.ardnn.mymovies.models.MovieOutline

interface PopularMoviesCallback {
    fun onSuccess(popularMoviesList: List<MovieOutline>)
    fun onFailure(message: String)
}