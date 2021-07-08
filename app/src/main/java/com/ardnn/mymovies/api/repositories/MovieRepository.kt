package com.ardnn.mymovies.api.repositories

import com.ardnn.mymovies.api.callbacks.movies.*
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.api.services.MovieApiServices
import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.Movie
import com.ardnn.mymovies.models.Video
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
    fun getNowPlayingMovies(page: Int, callback: MovieOutlineCallback) {
        MOVIE_SERVICE.getNowPlayingMovies(Consts.API_KEY, page).enqueue(object : Callback<MovieOutline> {
            override fun onResponse(call: Call<MovieOutline>, response: Response<MovieOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.movieOutlineList != null) {
                            callback.onSuccess(response.body()?.movieOutlineList ?: mutableListOf())
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
                callback.onFailure(t.localizedMessage ?: "getNowPlayingMovies failure")
            }

        })
    }

    // method to get upcoming movies
    fun getUpcomingMovies(page: Int, callback: MovieOutlineCallback) {
        MOVIE_SERVICE.getUpcomingMovies(Consts.API_KEY, page).enqueue(object : Callback<MovieOutline> {
            override fun onResponse(call: Call<MovieOutline>, response: Response<MovieOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.movieOutlineList != null) {
                            callback.onSuccess(response.body()?.movieOutlineList ?: mutableListOf())
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
                callback.onFailure(t.localizedMessage ?: "getUpcomingMovies failure")
            }
        })
    }

    // method to get popular movies
    fun getPopularMovies(page: Int, callback: MovieOutlineCallback) {
        MOVIE_SERVICE.getPopularMovies(Consts.API_KEY, page).enqueue(object : Callback<MovieOutline> {
            override fun onResponse(call: Call<MovieOutline>, response: Response<MovieOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.movieOutlineList != null) {
                            callback.onSuccess(response.body()?.movieOutlineList ?: mutableListOf())
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
                callback.onFailure(t.localizedMessage ?: "getPopularMovies failure")
            }
        })
    }

    // method to get top rated movies
    fun getTopRatedMovies(page: Int, callback: MovieOutlineCallback) {
        MOVIE_SERVICE.getTopRatedMovies(Consts.API_KEY, page).enqueue(object : Callback<MovieOutline> {
            override fun onResponse(call: Call<MovieOutline>, response: Response<MovieOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.movieOutlineList != null) {
                            callback.onSuccess(response.body()?.movieOutlineList ?: mutableListOf())
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
                callback.onFailure(t.localizedMessage ?: "getTopRatedMovies failure")
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
                            callback.onSuccess(response.body() ?: Movie())
                        } else {
                            callback.onFailure("response.body() is null")
                        }
                    } else {
                        callback.onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    callback.onFailure(t.localizedMessage ?: "getMovieDetails failure")
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
                                callback.onSuccess(response.body()?.castList ?: mutableListOf())
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
                    callback.onFailure(t.localizedMessage ?: "getMovieCasts failure")
                }

            })
    }

    // method to get movie videos
    fun getMovieVideos(movieId: Int, callback: MovieVideosCallback) {
        MOVIE_SERVICE.getMovieVideos(movieId, Consts.API_KEY)
            .enqueue(object : Callback<Video> {
                override fun onResponse(call: Call<Video>, response: Response<Video>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.videoList != null) {
                                callback.onSuccess(response.body()?.videoList ?: mutableListOf())
                            } else {
                                callback.onFailure("response.body().videoList is null")
                            }
                        } else {
                            callback.onFailure("response.body() is null")
                        }
                    } else {
                        callback.onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<Video>, t: Throwable) {
                    callback.onFailure(t.localizedMessage ?: "Response failure.")
                }

            })
    }

    // method to get similar movies
    fun getSimilarMovies(movieId: Int, callback: MovieOutlineCallback) {
        MOVIE_SERVICE.getSimilarMovies(movieId, Consts.API_KEY)
            .enqueue(object : Callback<MovieOutline> {
                override fun onResponse(
                    call: Call<MovieOutline>,
                    response: Response<MovieOutline>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.movieOutlineList != null) {
                                callback.onSuccess(response.body()?.movieOutlineList ?: mutableListOf())
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
                    callback.onFailure(t.localizedMessage ?: "getSimilarMovies failure")
                }

            })
    }
}