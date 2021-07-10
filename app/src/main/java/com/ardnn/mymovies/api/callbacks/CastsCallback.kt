package com.ardnn.mymovies.api.callbacks

import com.ardnn.mymovies.models.Cast

interface CastsCallback {
    fun onSuccess(castList: List<Cast>)
    fun onFailure(message: String)
}