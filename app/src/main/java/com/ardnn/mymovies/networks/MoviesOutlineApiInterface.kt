package com.ardnn.mymovies.networks

import com.ardnn.mymovies.models.MoviesOutlineResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesOutlineApiInterface {
    @GET("now_playing")
    fun getNowPlayingMovies(
        @Query("api_key")
        apiKey: String
    ): Call<MoviesOutlineResponse>

    @GET("upcoming")
    fun getUpcomingMovies(
        @Query("api_key")
        apiKey: String
    ): Call<MoviesOutlineResponse>

    @GET("popular")
    fun getPopularMovies(
        @Query("api_key")
        apiKey: String
    ): Call<MoviesOutlineResponse>

    @GET("top_rated")
    fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String
    ): Call<MoviesOutlineResponse>
}