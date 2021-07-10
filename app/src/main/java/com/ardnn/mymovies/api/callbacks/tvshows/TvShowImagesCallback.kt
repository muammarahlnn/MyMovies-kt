package com.ardnn.mymovies.api.callbacks.tvshows

import com.ardnn.mymovies.models.Image

interface TvShowImagesCallback {
    fun onPostersSuccess(posterList: List<Image>)
    fun onBackdropsSuccess(backdropList: List<Image>)
    fun onFailure(message: String)
}