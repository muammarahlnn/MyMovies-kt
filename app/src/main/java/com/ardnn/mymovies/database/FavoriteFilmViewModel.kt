package com.ardnn.mymovies.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.database.entities.FavoriteTvShows
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteFilmViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteFilmRepository =
        FavoriteFilmRepository(AppDatabase.getDatabase(application).favoriteFilmDao())

    // movies
    val favoriteMovieList: LiveData<List<FavoriteMovies>> = repository.favoriteMovieList

    fun addMovie(favoriteMovie: FavoriteMovies) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovie(favoriteMovie)
        }
    }

    fun deleteMovie(favoriteMovie: FavoriteMovies) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(favoriteMovie)
        }
    }

    suspend fun getMovie(id: Int): FavoriteMovies {
        return repository.getMovie(id)
    }

    suspend fun isMovieExists(id: Int): Boolean {
        return repository.isMovieExists(id)
    }


    // tv shows
    val favoriteTvShowList: LiveData<List<FavoriteTvShows>> = repository.favoriteTvShowList

    fun addTvShow(favoriteTvShow: FavoriteTvShows) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTvShow(favoriteTvShow)
        }
    }

    fun deleteTvShow(favoriteTvShow: FavoriteTvShows) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTvShow(favoriteTvShow)
        }
    }

    suspend fun getTvShow(id: Int): FavoriteTvShows {
        return repository.getTvShow(id)
    }

    suspend fun isTvShowExists(id: Int): Boolean {
        return repository.isTvShowExists(id)
    }



}