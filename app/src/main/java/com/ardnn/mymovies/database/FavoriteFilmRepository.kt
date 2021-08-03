package com.ardnn.mymovies.database

import androidx.lifecycle.LiveData
import com.ardnn.mymovies.database.dao.FavoriteFilmDao
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.database.entities.FavoriteTvShows

class FavoriteFilmRepository(private val dao: FavoriteFilmDao) {
    // favorite movies
    val favoriteMovieList: LiveData<List<FavoriteMovies>> = dao.getAllMovies()

    suspend fun addMovie(favoriteMovie: FavoriteMovies) {
        dao.addMovie(favoriteMovie)
    }

    suspend fun deleteMovie(favoriteMovie: FavoriteMovies) {
        dao.deleteMovie(favoriteMovie)
    }

    suspend fun getMovie(id: Int): FavoriteMovies {
        return dao.getMovie(id)
    }

    suspend fun isMovieExists(id: Int): Boolean {
        return dao.isMovieExist(id)
    }

    // tv shows
    val favoriteTvShowList: LiveData<List<FavoriteTvShows>> = dao.getAllTvShows()

    suspend fun addTvShow(favoriteTvShow: FavoriteTvShows) {
        dao.addTvShow(favoriteTvShow)
    }

    suspend fun deleteTvShow(favoriteTvShow: FavoriteTvShows) {
        dao.deleteTvShow(favoriteTvShow)
    }

    fun getTvShow(id: Int): FavoriteTvShows {
        return dao.getTvShow(id)
    }

    fun isTvShowExists(id: Int): Boolean {
        return dao.isTvShowExists(id)
    }
}