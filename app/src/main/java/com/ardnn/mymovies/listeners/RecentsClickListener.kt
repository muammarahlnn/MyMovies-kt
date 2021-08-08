package com.ardnn.mymovies.listeners

import com.ardnn.mymovies.database.entities.RecentFilms

interface RecentsClickListener {
    fun itemClicked(recentFilm: RecentFilms)
    fun btnDeleteClicked(recentFilm: RecentFilms)
}