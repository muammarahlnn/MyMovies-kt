package com.ardnn.mymovies.api.callbacks.movies

import com.ardnn.mymovies.models.MovieOutline

interface SimilarMoviesCallback {
    fun onSuccess(similarList: MutableList<MovieOutline>)
    fun onFailure(message: String)
}