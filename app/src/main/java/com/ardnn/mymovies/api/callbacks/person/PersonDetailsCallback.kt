package com.ardnn.mymovies.api.callbacks.person

import com.ardnn.mymovies.models.Person

interface PersonDetailsCallback {
    fun onSuccess(person: Person)
    fun onFailure(message: String)
}