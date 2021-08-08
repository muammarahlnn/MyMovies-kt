package com.ardnn.mymovies.database.repositories

import androidx.lifecycle.LiveData
import com.ardnn.mymovies.database.dao.RecentFilmDao
import com.ardnn.mymovies.database.entities.RecentFilms

class RecentFilmRepository(private val dao: RecentFilmDao) {
    val recentFilmList: LiveData<List<RecentFilms>> = dao.getAllRecentFilms()

    suspend fun addRecentFilm(recentFilm: RecentFilms) {
        dao.addRecentFilm(recentFilm)
    }

    suspend fun deleteRecentFilm(recentFilm: RecentFilms) {
        dao.deleteRecentFilm(recentFilm)
    }

    suspend fun deleteAllRecentFilms() {
        dao.deleteAllRecentFilms()
    }

    suspend fun getRecentMovie(movieId: Int): RecentFilms {
        return dao.getRecentMovie(movieId)
    }

    suspend fun getRecentTvShow(tvShowId: Int): RecentFilms {
        return dao.getRecentTvShow(tvShowId)
    }

    suspend fun isRecentMovieExists(movieId: Int): Boolean {
        return dao.isRecentMovieExists(movieId)
    }

    suspend fun isRecentTvShowExists(tvShowId: Int): Boolean {
        return dao.isRecentTvShowExists(tvShowId)
    }
}