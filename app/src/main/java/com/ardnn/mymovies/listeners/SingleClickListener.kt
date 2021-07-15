package com.ardnn.mymovies.listeners

// this interface is for contexts that only need one click listener
interface SingleClickListener<T> {
    fun onItemClicked(item: T)
}