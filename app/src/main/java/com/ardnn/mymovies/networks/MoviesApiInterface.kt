package com.ardnn.mymovies.networks

import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.Movie
import com.ardnn.mymovies.models.MovieOutlineResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiInterface {
    @GET("{movie_id}")
    fun getDetailMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<Movie>

    @GET("{movie_id}/credits")
    fun getMoviesCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<Cast>

    @GET("now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String
    ): Call<MovieOutlineResponse>

    @GET("upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String
    ): Call<MovieOutlineResponse>

    @GET("popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Call<MovieOutlineResponse>

    @GET("top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ): Call<MovieOutlineResponse>
}