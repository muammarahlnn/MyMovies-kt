package com.ardnn.mymovies.networks

import com.ardnn.mymovies.helpers.Utils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TvShowsApiClient {
    val retrofit: Retrofit
        get() {
            return Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_TV_SHOW)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
}