package com.ardnn.mymovies.api.callbacks.movies

import com.ardnn.mymovies.models.Image

interface MovieImagesCallback {
    fun onPostersSuccess(posterList: List<Image>)
    fun onBackdropsSuccess(backdropList: List<Image>)
    fun onFailure(message: String)
}