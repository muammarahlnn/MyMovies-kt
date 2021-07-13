package com.ardnn.mymovies.models

import com.ardnn.mymovies.api.repositories.Consts
import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("character")
    val character: String?,

    @SerializedName("profile_path")
    val imageUrl: String?,

    @SerializedName("cast")
    val castList: List<Cast>
) {
    fun getImageUrl(size: ImageSize) : String {
        return "${Consts.IMG_URL}${size.getValue()}${imageUrl}"
    }
}

