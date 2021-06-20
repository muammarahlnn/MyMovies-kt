package com.ardnn.mymovies.helpers

import com.ardnn.mymovies.adapters.MoviesOutlineAdapter
import com.ardnn.mymovies.models.MoviesOutline
import com.ardnn.mymovies.models.MoviesOutlineResponse
import com.ardnn.mymovies.networks.MoviesOutlineApiClient
import com.ardnn.mymovies.networks.MoviesOutlineApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Fetch {

    // its still error
    fun loadMoviesData(responseCall: Call<MoviesOutlineResponse>, list: List<MoviesOutline>, adapter: MoviesOutlineAdapter) {
        val moviesOutlineApiInterface: MoviesOutlineApiInterface = MoviesOutlineApiClient.retrofit
            .create(MoviesOutlineApiInterface::class.java)

        responseCall.enqueue(object : Callback<MoviesOutlineResponse> {
            override fun onResponse(
                call: Call<MoviesOutlineResponse>,
                response: Response<MoviesOutlineResponse>
            ) {
                if (response.isSuccessful && response.body()?.moviesOutlineList != null) {
                    // put movies' data to list

                    // set recyclerview

                } else {
                    // response failed.
                }
            }

            override fun onFailure(call: Call<MoviesOutlineResponse>, t: Throwable) {
                // response failure
            }

        })
    }
}