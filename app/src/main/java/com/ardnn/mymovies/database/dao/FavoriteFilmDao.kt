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

    @Query("SELECT * FROM favorite_movies")
    fun getAllMovies(): LiveData<List<FavoriteMovies>>

    @Query("SELECT * FROM favorite_movies WHERE id = :id LIMIT 1")
    fun getMovie(id: Int): FavoriteMovies

    @Query("SELECT EXISTS (SELECT * FROM favorite_movies WHERE id = :id)")
    fun isMovieExist(id: Int): Boolean

    // favorite tv shows ==================================================
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTvShow(favoriteTvShow: FavoriteTvShows)

    @Delete
    suspend fun deleteTvShow(favoriteTvShow: FavoriteTvShows)

    @Query("SELECT * FROM favorite_tv_shows")
    fun getAllTvShows(): LiveData<List<FavoriteTvShows>>

    @Query("SELECT * FROM favorite_tv_shows WHERE id = :id LIMIT 1")
    fun getTvShow(id: Int): FavoriteTvShows

    @Query("SELECT EXISTS (SELECT * FROM favorite_tv_shows WHERE id = :id)")
    fun isTvShowExists(id: Int): Boolean

}