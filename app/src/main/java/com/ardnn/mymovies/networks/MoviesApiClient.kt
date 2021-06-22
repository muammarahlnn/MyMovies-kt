package com.ardnn.mymovies.networks

import com.ardnn.mymovies.helpers.Utils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesApiClient {
    val retrofit: Retrofit
        get() {
            return Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_MOVIE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

}