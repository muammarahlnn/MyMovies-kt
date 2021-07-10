package com.ardnn.mymovies.models

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("aspect_ratio")
    val aspectRatio: Float? = -1F,

    @SerializedName("height")
    val height: Int? = -1,

    @SerializedName("width")
    val width: Int? = -1,

    @SerializedName("file_path")
    val imageUrl: String? = "",

    @SerializedName("backdrops")
    val backdropList: List<Image>,

    @SerializedName("posters")
    val posterList: List<Image>,
)