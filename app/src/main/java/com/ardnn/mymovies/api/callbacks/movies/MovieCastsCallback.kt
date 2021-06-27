package com.ardnn.mymovies.api.callbacks.movies

import com.ardnn.mymovies.models.Cast

interface MovieCastsCallback {
    fun onSuccess(castList: List<Cast>)
    fun onFailure(message: String)
}