package com.ardnn.mymovies.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.mymovies.fragments.tvshows.*

class TvShowsPagerAdapter(fragmentActivity: FragmentActivity?) :
    FragmentStateAdapter(fragmentActivity!!) {

    override fun createFragment(position: Int): Fragment {
        return TvShowsFragment.newInstance(position)
    }

    override fun getItemCount(): Int {
        return TvShowsPagerFragment.tabTitles.size
    }

}