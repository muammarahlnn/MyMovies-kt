package com.ardnn.mymovies.api.services

import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.TvShow
import com.ardnn.mymovies.models.TvShowOutline
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApiServices {
    @GET("{tv_id}")
    fun getTvShowDetails(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<TvShow>

    @GET("{tv_id}/credits")
    fun getTvShowCasts(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<Cast>

    @GET("airing_today")
    fun getAiringTodayTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<TvShowOutline>

    @GET("on_the_air")
    fun getOnTheAirTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<TvShowOutline>

    @GET("popular")
    fun getPopularTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<TvShowOutline>

    @GET("top_rated")
    fun getTopRatedTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<TvShowOutline>
}