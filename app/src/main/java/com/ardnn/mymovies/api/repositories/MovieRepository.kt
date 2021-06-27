package com.ardnn.mymovies.api.repositories

import android.widget.TextView
import com.ardnn.mymovies.api.callbacks.movies.*
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.api.services.MovieApiServices
import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRepository {
    private val MOVIE_SERVICE: MovieApiServices =
        Retrofit.Builder()
            .baseUrl(Consts.BASE_URL_MOVIE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiServices::class.java)


    // method to get now playing movies
    fun getNowPlayingMovies(callback: NowPlayingMoviesCallback) {
        MOVIE_SERVICE.getNowPlayingMovies(Consts.API_KEY).enqueue(object : Callback<MovieOutline> {
            override fun onResponse(call: Call<MovieOutline>, response: Response<MovieOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.movieOutlineList != null) {
                            callback.onSuccess(response.body()!!.movieOutlineList)
                        } else {
                            callback.onFailure("response.body().movieOutlineList is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MovieOutline>, t: Throwable) {
                callback.onFailure(t.localizedMessage!!)
            }

        })
    }

    // method to get upcoming movies
    fun getUpcomingMovies(callback: UpcomingMoviesCallback) {
        MOVIE_SERVICE.getUpcomingMovies(Consts.API_KEY).enqueue(object : Callback<MovieOutline> {
            override fun onResponse(call: Call<MovieOutline>, response: Response<MovieOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.movieOutlineList != null) {
                            callback.onSuccess(response.body()!!.movieOutlineList)
                        } else {
                            callback.onFailure("response.body().movieOutlineList is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MovieOutline>, t: Throwable) {
                callback.onFailure(t.localizedMessage!!)
            }
        })
    }

    // method to get popular movies
    fun getPopularMovies(callback: PopularMoviesCallback) {
        MOVIE_SERVICE.getPopularMovies(Consts.API_KEY).enqueue(object : Callback<MovieOutline> {
            override fun onResponse(call: Call<MovieOutline>, response: Response<MovieOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.movieOutlineList != null) {
                            callback.onSuccess(response.body()!!.movieOutlineList)
                        } else {
                            callback.onFailure("response.body().movieOutlineList is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MovieOutline>, t: Throwable) {
                callback.onFailure(t.localizedMessage!!)
            }
        })
    }

    // method to get top rated movies
    fun getTopRatedMovies(callback: TopRatedMoviesCallback) {
        MOVIE_SERVICE.getTopRatedMovies(Consts.API_KEY).enqueue(object : Callback<MovieOutline> {
            override fun onResponse(call: Call<MovieOutline>, response: Response<MovieOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.movieOutlineList != null) {
                            callback.onSuccess(response.body()!!.movieOutlineList)
                        } else {
                            callback.onFailure("response.body().movieOutlineList is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MovieOutline>, t: Throwable) {
                callback.onFailure(t.localizedMessage!!)
            }
        })
    }

    // method to get movie details
    fun getMovieDetails(movieId: Int, callback: MovieDetailsCallback) {
        MOVIE_SERVICE.getMovieDetails(movieId, Consts.API_KEY)
            .enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            callback.onSuccess(response.body()!!)
                        } else {
                            callback.onFailure("response.body() is null")
                        }
                    } else {
                        callback.onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    callback.onFailure(t.localizedMessage!!)
                }
            })
    }

    // method to get movie casts
    fun getMovieCasts(movieId: Int, callback: MovieCastsCallback) {
        MOVIE_SERVICE.getMovieCasts(movieId, Consts.API_KEY)
            .enqueue(object : Callback<Cast> {
                override fun onResponse(call: Call<Cast>, response: Response<Cast>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.castList != null) {
                                callback.onSuccess(response.body()!!.castList)
                            } else {
                                callback.onFailure("response.body().castList is null")
                            }
                        } else {
                            callback.onFailure("response.body() is null")
                        }
                    } else {
                        callback.onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<Cast>, t: Throwable) {
                    callback.onFailure(t.localizedMessage!!)
                }

            })
    }

}