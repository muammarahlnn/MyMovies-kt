package com.ardnn.mymovies.networks

import com.ardnn.mymovies.models.MoviesNowPlayingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesNowPlayingApiInterface {
    @GET("now_playing")
    fun getMoviesNowPlaying(
        @Query("api_key")
        apiKey: String
    ): Call<MoviesNowPlayingResponse>
}