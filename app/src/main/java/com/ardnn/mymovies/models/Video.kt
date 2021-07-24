package com.ardnn.mymovies.models

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("key")
    val key: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("site")
    val site: String?,

    @SerializedName("results")
    val videoList: List<Video>
) {
    fun getImageUrl(): String {
        return "https://i.ytimg.com/vi/$key/mqdefault.jpg"
//        return "http://img.youtube.com/vi/${key}/hqdefault.jpg"
    }
}