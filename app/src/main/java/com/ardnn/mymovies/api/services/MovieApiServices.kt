package com.ardnn.mymovies.api.services

import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.Movie
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.models.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiServices {
    @GET("{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<Movie>

    @GET("{movie_id}/credits")
    fun getMovieCasts(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<Cast>

    @GET("{movie_id}/videos")
    fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<Video>

    @GET("now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<MovieOutline>

    @GET("upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<MovieOutline>

    @GET("popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<MovieOutline>

    @GET("top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<MovieOutline>
}