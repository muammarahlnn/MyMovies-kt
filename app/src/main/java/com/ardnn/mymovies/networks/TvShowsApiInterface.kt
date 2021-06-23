package com.ardnn.mymovies.networks

import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.Movie
import com.ardnn.mymovies.models.TvShow
import com.ardnn.mymovies.models.TvShowsOutlineResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsApiInterface {
    @GET("{tv_id}")
    fun getDetailTvShows(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<TvShow>

    @GET("{tv_id}/credits")
    fun getTvShowsCast(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<Cast>

    @GET("airing_today")
    fun getAiringTodayTvShows(
        @Query("api_key") apiKey: String
    ): Call<TvShowsOutlineResponse>

    @GET("on_the_air")
    fun getOnTheAirTvShows(
        @Query("api_key") apiKey: String
    ): Call<TvShowsOutlineResponse>

    @GET("popular")
    fun getPopularTvShows(
        @Query("api_key") apiKey: String
    ): Call<TvShowsOutlineResponse>

    @GET("top_rated")
    fun getTopRatedTvShows(
        @Query("api_key") apiKey: String
    ): Call<TvShowsOutlineResponse>
}