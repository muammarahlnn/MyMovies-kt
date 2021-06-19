package com.ardnn.mymovies.helpers

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout

object Utils {
    // consts
    const val BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/"
    const val BASE_URL_TV_SHOW = "https://api.themoviedb.org/3/tv/"
    const val API_KEY = "ddd13ad36a890e5208e5bad8ac1cc614"
    const val IMG_URL_300 = "https://image.tmdb.org/t/p/w300/"
    const val IMG_URL_500 = "https://image.tmdb.org/t/p/w500/"

    fun convertToDate(date: String): String {
        val months = listOf("",
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        )
        val splittedDate = date.split("-") // [year, month, day]
        val year = splittedDate[0]
        val month = months[splittedDate[1].toInt()]
        var day = splittedDate[2]

        // check if day contains leading zeros
        if (day[0] == '0') {
            day = day.substring(1)
        }

        return "$day $month, $year"
    }

    fun equalingEachTabWidth(tabLayout: TabLayout) {
        val slidingTab: ViewGroup = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabLayout.tabCount) {
            val tab: View = slidingTab.getChildAt(i)
            val layoutParams: LinearLayout.LayoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 1F
            tab.layoutParams = layoutParams
        }
    }
}