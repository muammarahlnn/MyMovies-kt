package com.ardnn.mymovies.networks

import com.ardnn.mymovies.helpers.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesNowPlayingApiClient {
    val retrofit: Retrofit
        get() {
            return Retrofit.Builder()
                .baseUrl(Const.BASE_URL_MOVIE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

}