package com.ardnn.mymovies.api.repositories

import com.ardnn.mymovies.api.callbacks.tvshows.*
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.TvShowOutline
import com.ardnn.mymovies.api.services.TvShowApiServices
import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.TvShow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TvShowRepository {
    private val TV_SHOW_SERVICE: TvShowApiServices =
        Retrofit.Builder()
            .baseUrl(Consts.BASE_URL_TV_SHOW)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvShowApiServices::class.java)


    // method to get airing today tv shows
    fun getAiringTodayTvShows(callback: AiringTodayTvShowsCallback) {
        TV_SHOW_SERVICE.getAiringTodayTvShows(Consts.API_KEY).enqueue(object : Callback<TvShowOutline> {
            override fun onResponse(call: Call<TvShowOutline>, response: Response<TvShowOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.tvShowOutlineList != null) {
                            callback.onSuccess(response.body()!!.tvShowOutlineList)
                        } else {
                            callback.onFailure("response.body().tvShowOutlineList is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<TvShowOutline>, t: Throwable) {
                callback.onFailure(t.localizedMessage!!)
            }

        })
    }

    // method to get on the air tv shows
    fun getOnTheAirTvShows(callback: OnTheAirTvShowsCallback) {
        TV_SHOW_SERVICE.getOnTheAirTvShows(Consts.API_KEY).enqueue(object : Callback<TvShowOutline> {
            override fun onResponse(call: Call<TvShowOutline>, response: Response<TvShowOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.tvShowOutlineList != null) {
                            callback.onSuccess(response.body()!!.tvShowOutlineList)
                        } else {
                            callback.onFailure("response.body().tvShowOutlineList is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<TvShowOutline>, t: Throwable) {
                callback.onFailure(t.localizedMessage!!)
            }
        })
    }

    // method to get popular tv shows
    fun getPopularTvShows(callback: PopularTvShowsCallback) {
        TV_SHOW_SERVICE.getPopularTvShows(Consts.API_KEY).enqueue(object : Callback<TvShowOutline> {
            override fun onResponse(call: Call<TvShowOutline>, response: Response<TvShowOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.tvShowOutlineList != null) {
                            callback.onSuccess(response.body()!!.tvShowOutlineList)
                        } else {
                            callback.onFailure("response.body().tvShowOutlineList is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<TvShowOutline>, t: Throwable) {
                callback.onFailure(t.localizedMessage!!)
            }

        })
    }

    // method to get top rated tv shows
    fun getTopRatedTvShows(callback: TopRatedTvShowsCallback) {
        TV_SHOW_SERVICE.getTopRatedTvShows(Consts.API_KEY).enqueue(object : Callback<TvShowOutline> {
            override fun onResponse(call: Call<TvShowOutline>, response: Response<TvShowOutline>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.tvShowOutlineList != null) {
                            callback.onSuccess(response.body()!!.tvShowOutlineList)
                        } else {
                            callback.onFailure("response.body().tvShowOutlineList is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<TvShowOutline>, t: Throwable) {
                callback.onFailure(t.localizedMessage!!)
            }

        })
    }

    // method to get tv show detail
    fun getTvShowDetails(tvShowId: Int, callback: TvShowDetailsCallback) {
        TV_SHOW_SERVICE.getTvShowDetails(tvShowId, Consts.API_KEY)
            .enqueue(object : Callback<TvShow> {
                override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
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

                override fun onFailure(call: Call<TvShow>, t: Throwable) {
                    callback.onFailure(t.localizedMessage!!)
                }
        })
    }

    // method to get tv show casts
    fun getTvShowCasts(tvShowId: Int, callback: TvShowCastsCallback) {
        TV_SHOW_SERVICE.getTvShowCasts(tvShowId, Consts.API_KEY)
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