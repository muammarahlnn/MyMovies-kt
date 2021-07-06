package com.ardnn.mymovies.api.callbacks.person

import com.ardnn.mymovies.models.TvShowOutline

interface PersonTvShowsCallback {
    fun onSuccess(personTvShowList: MutableList<TvShowOutline>)
    fun onFailure(message: String)
}