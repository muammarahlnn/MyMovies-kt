package com.ardnn.mymovies.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.database.entities.FavoriteTvShows

@Dao
interface FavoriteFilmDao {

    // favorite movies ==================================================
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(favoriteMovie: FavoriteMovies)

    @Delete
    suspend fun deleteMovie(favoriteMovie: FavoriteMovies)

    @Query("SELECT * FROM favorite_movies WHERE id = :id LIMIT 1")
    suspend fun getMovie(id: Int): FavoriteMovies

    @Query("SELECT EXISTS (SELECT * FROM favorite_movies WHERE id = :id)")
    suspend fun isMovieExist(id: Int): Boolean

    @Query("DELETE FROM favorite_movies")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM favorite_movies")
    fun getAllMovies(): LiveData<List<FavoriteMovies>>

    // favorite tv shows ==================================================
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTvShow(favoriteTvShow: FavoriteTvShows)

    @Delete
    suspend fun deleteTvShow(favoriteTvShow: FavoriteTvShows)

    @Query("SELECT * FROM favorite_tv_shows WHERE id = :id LIMIT 1")
    suspend fun getTvShow(id: Int): FavoriteTvShows

    @Query("SELECT EXISTS (SELECT * FROM favorite_tv_shows WHERE id = :id)")
    suspend fun isTvShowExists(id: Int): Boolean

    @Query("DELETE FROM favorite_tv_shows")
    suspend fun deleteAllTvShows()

    @Query("SELECT * FROM favorite_tv_shows")
    fun getAllTvShows(): LiveData<List<FavoriteTvShows>>
}