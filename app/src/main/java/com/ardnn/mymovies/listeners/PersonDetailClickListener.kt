package com.ardnn.mymovies.listeners

import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.models.TvShowOutline

interface PersonDetailClickListener {
    fun onMovieClicked(movie: MovieOutline)
    fun onTvShowClicked(tvShow: TvShowOutline)
}