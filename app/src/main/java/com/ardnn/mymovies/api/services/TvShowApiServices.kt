package com.ardnn.mymovies.api.services

import com.ardnn.mymovies.models.*
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

    @GET("{tv_id}/images")
    fun getTvShowImages(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en",
    ): Call<Image>

    @GET("{tv_id}/videos")
    fun getTvShowVideos(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<Video>

    @GET("{tv_id}/similar")
    fun getSimilarTvShows(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<TvShowOutline>

    @GET("{tv_id}/recommendations")
    fun getTvShowRecommendations(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<TvShowOutline>

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