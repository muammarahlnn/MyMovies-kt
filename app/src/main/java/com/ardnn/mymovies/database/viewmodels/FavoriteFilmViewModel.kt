package com.ardnn.mymovies.database.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ardnn.mymovies.database.AppDatabase
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.database.entities.FavoriteTvShows
import com.ardnn.mymovies.database.repositories.FavoriteFilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFilmViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteFilmRepository =
        FavoriteFilmRepository(AppDatabase.getDatabase(application).favoriteFilmDao())

    // movies ==========================================================================
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

    fun deleteAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllMovies()
        }
    }

    suspend fun getMovie(id: Int): FavoriteMovies {
        return repository.getMovie(id)
    }

    suspend fun isMovieExists(id: Int): Boolean {
        return repository.isMovieExists(id)
    }


    // tv shows ===========================================================================
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

    fun deleteALlTvShows() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTvShows()
        }
    }

    suspend fun getTvShow(id: Int): FavoriteTvShows {
        return repository.getTvShow(id)
    }

    suspend fun isTvShowExists(id: Int): Boolean {
        return repository.isTvShowExists(id)
    }



}