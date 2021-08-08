package com.ardnn.mymovies.database.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ardnn.mymovies.database.AppDatabase
import com.ardnn.mymovies.database.entities.RecentFilms
import com.ardnn.mymovies.database.repositories.RecentFilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentFilmViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecentFilmRepository =
        RecentFilmRepository(AppDatabase.getDatabase(application).recentFilmDao())

    val recentFilmList: LiveData<List<RecentFilms>> = repository.recentFilmList

    fun addRecentFilm(recentFilm: RecentFilms) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRecentFilm(recentFilm)
        }
    }

    fun deleteRecentFilm(recentFilm: RecentFilms) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRecentFilm(recentFilm)
        }
    }

    fun deleteAllRecentFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllRecentFilms()
        }
    }

    suspend fun getRecentMovie(movieId: Int): RecentFilms {
        return repository.getRecentMovie(movieId)
    }

    suspend fun getRecentTvShow(tvShowId: Int): RecentFilms {
        return repository.getRecentTvShow(tvShowId)
    }

    suspend fun isRecentMovieExists(movieId: Int): Boolean {
        return repository.isRecentMovieExists(movieId)
    }

    suspend fun isRecentTvShowExists(tvShowId: Int): Boolean {
        return repository.isRecentTvShowExists(tvShowId)
    }
}