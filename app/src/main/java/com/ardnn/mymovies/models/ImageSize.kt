package com.ardnn.mymovies.models

enum class ImageSize(private val size: String) {
    W92("w92"),
    W154("w154"),
    W185("w185"),
    W200("w200"),
    W342("w342"),
    W500("w500"),
    W780("w780"),
    ORI("original");

    fun getValue(): String {
        return size
    }

}