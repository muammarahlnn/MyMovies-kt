package com.ardnn.mymovies.adapters

interface OnItemClick<T> {
    fun itemClicked(data: T)
}