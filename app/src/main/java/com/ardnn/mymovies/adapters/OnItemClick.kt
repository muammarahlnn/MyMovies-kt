package com.ardnn.mymovies.adapters

import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.Genre
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.models.TvShowOutline

interface OnItemClick {
    fun itemClicked(movieOutline: MovieOutline)
    fun itemClicked(tvShowOutline: TvShowOutline)
    fun itemClicked(genre: Genre)
    fun itemClicked(cast: Cast)
}