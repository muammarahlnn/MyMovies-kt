package com.ardnn.mymovies.listeners

import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.database.entities.FavoriteTvShows

interface FavoriteClickListener {
    fun onItemClicked(favoriteMovie: FavoriteMovies)
    fun onItemClicked(favoriteTvShow: FavoriteTvShows)
}