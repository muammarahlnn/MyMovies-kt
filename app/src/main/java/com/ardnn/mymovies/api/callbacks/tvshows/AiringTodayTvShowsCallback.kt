package com.ardnn.mymovies.api.callbacks.tvshows

import com.ardnn.mymovies.models.TvShowOutline

interface AiringTodayTvShowsCallback {
    fun onSuccess(airingTodayList: List<TvShowOutline>)
    fun onFailure(message: String)
}