package com.ardnn.mymovies.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.mymovies.fragments.tvshows.AiringTodayFragment
import com.ardnn.mymovies.fragments.tvshows.OnTheAirFragment
import com.ardnn.mymovies.fragments.tvshows.PopularTvShowsFragment
import com.ardnn.mymovies.fragments.tvshows.TopRatedTvShowsFragment

class TvShowsPagerAdapter(fragmentActivity: FragmentActivity?) :
    FragmentStateAdapter(fragmentActivity!!) {

    private val fragments: Array<Fragment> = arrayOf(
        AiringTodayFragment(),
        OnTheAirFragment(),
        PopularTvShowsFragment(),
        TopRatedTvShowsFragment()
    )

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

}