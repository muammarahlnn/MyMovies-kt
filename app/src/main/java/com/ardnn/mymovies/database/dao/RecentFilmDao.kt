package com.ardnn.mymovies.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ardnn.mymovies.database.entities.RecentFilms

@Dao
interface RecentFilmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecentFilm(recentFilm: RecentFilms)

    @Delete
    suspend fun deleteRecentFilm(recentFilm: RecentFilms)

    @Query("SELECT * FROM recent_films WHERE movie_id = :movieId LIMIT 1")
    suspend fun getRecentMovie(movieId: Int): RecentFilms

    @Query("SELECT * FROM recent_films WHERE tv_show_id = :tvShowId LIMIT 1")
    suspend fun getRecentTvShow(tvShowId: Int): RecentFilms

    @Query("SELECT EXISTS(SELECT * FROM recent_films WHERE movie_id = :movieId)")
    suspend fun isRecentMovieExists(movieId: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM recent_films WHERE tv_show_id = :tvShowId)")
    suspend fun isRecentTvShowExists(tvShowId: Int): Boolean

    @Query("DELETE FROM recent_films")
    suspend fun deleteAllRecentFilms()

    @Query("SELECT * FROM recent_films ORDER BY id DESC")
    fun getAllRecentFilms(): LiveData<List<RecentFilms>>
}