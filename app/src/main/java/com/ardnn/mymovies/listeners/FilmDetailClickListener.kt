package com.ardnn.mymovies.listeners

import com.ardnn.mymovies.models.*

interface FilmDetailClickListener {
    fun onGenreClicked(genre: Genre)
    fun onCastClicked(cast: Cast)
    fun onVideoClicked(video: Video)
    fun onSimilarClicked(movieOutline: MovieOutline)
    fun onSimilarClicked(tvShowOutline: TvShowOutline)
}