package com.ardnn.mymovies.api.callbacks

import com.ardnn.mymovies.models.Image

interface ImagesCallback {
    fun onPostersSuccess(posterList: List<Image>)
    fun onBackdropsSuccess(backdropList: List<Image>)
    fun onFailure(message: String)
}