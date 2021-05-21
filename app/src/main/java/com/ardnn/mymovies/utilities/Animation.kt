package com.ardnn.mymovies.utilities

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ardnn.mymovies.R

class Animation(private val context: Context) {
    val fadeIn: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)

}