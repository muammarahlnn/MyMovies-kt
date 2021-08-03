package com.ardnn.mymovies.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.database.entities.FavoriteTvShows
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFilmViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteFilmRepository =
        FavoriteFilmRepository(AppDatabase.getDatabase(application).favoriteFilmDao())

    // movies
    private val favoriteMovieList: LiveData<List<FavoriteMovies>> = repository.favoriteMovieList

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

    fun getMovie(id: Int): FavoriteMovies {
        return repository.getMovie(id)
    }

    fun isMovieExists(id: Int): Boolean {
        return repository.isMovieExists(id)
    }



    // tv shows
    private val favoriteTvShowList: LiveData<List<FavoriteTvShows>> = repository.favoriteTvShowList

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

    fun getTvShow(id: Int): FavoriteTvShows {
        return repository.getTvShow(id)
    }

    fun isTvShowExists(id: Int): Boolean {
        return repository.isTvShowExists(id)
    }



}