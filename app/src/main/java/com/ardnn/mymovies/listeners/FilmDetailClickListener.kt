package com.ardnn.mymovies.listeners

import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.Genre
import com.ardnn.mymovies.models.Video

interface FilmDetailClickListener {
    fun onGenreClicked(genre: Genre)
    fun onCastClicked(cast: Cast)
    fun onVideoClicked(video: Video)
}