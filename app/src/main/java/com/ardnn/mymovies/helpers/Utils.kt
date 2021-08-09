package com.ardnn.mymovies.helpers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.ardnn.mymovies.R
import com.ardnn.mymovies.models.ImageSize
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout

object Utils {
    private const val SHARED_PREFERENCES = "shared_preferences"
    private const val RERUN_KEY = "rerun_key"

    fun saveRerunFlag(context: Context, flag: Boolean) {
        val pref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putBoolean(RERUN_KEY, flag)
        editor.apply()
    }

    fun getRerunFlag(context: Context): Boolean {
        val pref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return pref.getBoolean(RERUN_KEY, false)
    }

    fun convertToDate(date: String?): String {
        if (date == null || date == "") return "-"

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

    fun setImageGlide(context: Context, url: String?, imageView: ImageView, withPlaceholder: Boolean = false) {
        val reqOption = RequestOptions()
            .placeholder(R.drawable.img_placeholder).centerCrop()

        if (withPlaceholder) {
            Glide.with(context).load(url)
                .apply(reqOption)
                .into(imageView)
        } else {
            Glide.with(context).load(url)
                .into(imageView)
        }
    }
}