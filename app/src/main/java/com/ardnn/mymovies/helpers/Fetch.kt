package com.ardnn.mymovies.helpers

import com.ardnn.mymovies.adapters.MoviesOutlineAdapter
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.networks.MoviesApiClient
import com.ardnn.mymovies.networks.MoviesApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Fetch {

    /* its still error
    fun loadMovieOutline(responseCall: Call<MovieOutline>, list: List<MovieOutline>, adapter: MoviesOutlineAdapter) {
        val moviesApiInterface: MoviesApiInterface = MoviesApiClient.retrofit
            .create(MoviesApiInterface::class.java)

        responseCall.enqueue(object : Callback<MovieOutline> {
            override fun onResponse(
                call: Call<MovieOutline>,
                response: Response<MovieOutline>
            ) {
                if (response.isSuccessful && response.body()?.movieOutlineList != null) {
                    // put movies' data to list

                    // set recyclerview

                } else {
                    // response failed.
                }
            }

            override fun onFailure(call: Call<MovieOutline>, t: Throwable) {
                // response failure
            }

        })
    }
    */
}