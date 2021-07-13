package com.ardnn.mymovies.api.callbacks

import com.ardnn.mymovies.models.Person

interface PersonDetailsCallback {
    fun onSuccess(person: Person)
    fun onFailure(message: String)
}