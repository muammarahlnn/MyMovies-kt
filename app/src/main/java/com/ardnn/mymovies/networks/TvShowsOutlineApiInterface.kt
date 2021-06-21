package com.ardnn.mymovies.networks

import com.ardnn.mymovies.models.TvShowsOutlineResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowsOutlineApiInterface {
    @GET("airing_today")
    fun getAiringTodayTvShows(
        @Query("api_key")
        apiKey: String
    ): Call<TvShowsOutlineResponse>

    @GET("on_the_air")
    fun getOnTheAirTvShows(
        @Query("api_key")
        apiKey: String
    ): Call<TvShowsOutlineResponse>

    @GET("popular")
    fun getPopularTvShows(
        @Query("api_key")
        apiKey: String
    ): Call<TvShowsOutlineResponse>

    @GET("top_rated")
    fun getTopRatedTvShows(
        @Query("api_key")
        apiKey: String
    ): Call<TvShowsOutlineResponse>
}