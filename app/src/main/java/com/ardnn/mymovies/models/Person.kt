package com.ardnn.mymovies.models

import com.ardnn.mymovies.api.repositories.Consts
import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("id")
    val id: Int? = -1,

    @SerializedName("name")
    val name: String? = "",

    @SerializedName("birthday")
    val birthday: String? = "",

    @SerializedName("place_of_birth")
    val birthPlace: String? = "",

    @SerializedName("gender")
    val genderCode: Int? = -1,

    @SerializedName("known_for_department")
    val department: String? = "",

    @SerializedName("profile_path")
    val profileUrl: String = "",

    @SerializedName("also_known_as")
    val akaList: List<String> = listOf(),

    @SerializedName("biography")
    val biography: String? = "",
) {
    fun getProfileUrl(size: ImageSize): String {
        return "${Consts.IMG_URL}${size.getValue()}${profileUrl}"
    }
}