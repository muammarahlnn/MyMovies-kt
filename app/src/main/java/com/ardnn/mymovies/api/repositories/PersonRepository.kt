package com.ardnn.mymovies.api.repositories

import com.ardnn.mymovies.api.callbacks.MovieOutlineCallback
import com.ardnn.mymovies.api.callbacks.PersonDetailsCallback
import com.ardnn.mymovies.api.callbacks.TvShowOutlineCallback
import com.ardnn.mymovies.api.services.PersonApiServices
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.models.Person
import com.ardnn.mymovies.models.TvShowOutline
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PersonRepository {
    private val PERSON_SERVICE: PersonApiServices =
        Retrofit.Builder()
            .baseUrl(Consts.BASE_URL_PERSON)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PersonApiServices::class.java)


    // method to get person details
    fun getPersonDetails(personId: Int, callback: PersonDetailsCallback) {
        PERSON_SERVICE.getPersonDetails(personId, Consts.API_KEY)
            .enqueue(object : Callback<Person> {
                override fun onResponse(call: Call<Person>, response: Response<Person>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            callback.onSuccess(response.body() ?: Person())
                        } else {
                            callback.onFailure("response.body() is null")
                        }
                    } else {
                        callback.onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<Person>, t: Throwable) {
                    callback.onFailure(t.localizedMessage ?: "getPersonDetails failure")
                }

            })
    }

    // method to get person movies
    fun getPersonMovies(personId: Int, callback: MovieOutlineCallback) {
        PERSON_SERVICE.getPersonMovies(personId, Consts.API_KEY)
            .enqueue(object : Callback<MovieOutline> {
                override fun onResponse(
                    call: Call<MovieOutline>,
                    response: Response<MovieOutline>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.personMovieList != null) {
                                callback.onSuccess(response.body()?.personMovieList ?: mutableListOf())
                            } else {
                                callback.onFailure("response.body().personMovieList is null")
                            }
                        } else {
                            callback.onFailure("response.body() is null")
                        }
                    } else {
                        callback.onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<MovieOutline>, t: Throwable) {
                    callback.onFailure(t.localizedMessage ?: "getPersonMovies failure")
                }
            })
    }

    // method to get person tv shows
    fun getPersonTvShows(personId: Int, callback: TvShowOutlineCallback) {
        PERSON_SERVICE.getPersonTvShows(personId, Consts.API_KEY)
            .enqueue(object : Callback<TvShowOutline> {
                override fun onResponse(
                    call: Call<TvShowOutline>,
                    response: Response<TvShowOutline>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.personTvShowList != null) {
                                callback.onSuccess(response.body()?.personTvShowList ?: mutableListOf())
                            } else {
                                callback.onFailure("response.body().personTvShowList is null")
                            }
                        } else {
                            callback.onFailure("response.body() is null")
                        }
                    } else {
                        callback.onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<TvShowOutline>, t: Throwable) {
                    callback.onFailure(t.localizedMessage ?: "getPersonTvShows failure")
                }

            })
    }
}