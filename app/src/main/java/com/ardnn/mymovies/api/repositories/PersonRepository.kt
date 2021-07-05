package com.ardnn.mymovies.api.repositories

import com.ardnn.mymovies.api.callbacks.person.PersonDetailsCallback
import com.ardnn.mymovies.api.services.PersonApiServices
import com.ardnn.mymovies.models.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PersonRepository {
    private val PERSON_SERVICE: PersonApiServices =
        Retrofit.Builder()
            .baseUrl(Consts.BASE_URL_PERSON)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PersonApiServices::class.java)


    // method to get person details
    fun getPersonDetails(personId: Int, callback: PersonDetailsCallback) {
        PERSON_SERVICE.getPersonDetails(personId, Consts.API_KEY)
            .enqueue(object : Callback<Person> {
                override fun onResponse(call: Call<Person>, response: Response<Person>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            callback.onSuccess(response.body()!!)
                        } else {
                            callback.onFailure("response.body() is null")
                        }
                    } else {
                        callback.onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<Person>, t: Throwable) {
                    callback.onFailure(t.localizedMessage!!)
                }

            })
    }
}