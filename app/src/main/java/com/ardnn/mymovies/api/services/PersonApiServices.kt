package com.ardnn.mymovies.api.services

import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.models.Person
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApiServices {
    @GET("{person_id}")
    fun getPersonDetails(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Call<Person>

    @GET("{person_id}/movie_credits")
    fun getPersonMovies(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieOutline>
}