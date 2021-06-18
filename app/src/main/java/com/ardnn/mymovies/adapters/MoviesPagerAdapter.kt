 package com.ardnn.mymovies.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.mymovies.fragments.*
import com.ardnn.mymovies.fragments.movies.NowPlayingFragment
import com.ardnn.mymovies.fragments.movies.PopularMoviesFragment
import com.ardnn.mymovies.fragments.movies.TopRatedMoviesFragment
import com.ardnn.mymovies.fragments.movies.UpcomingFragment

class MoviesPagerAdapter(fragmentActivity: FragmentActivity?) :
    FragmentStateAdapter(fragmentActivity!!) {

    private val fragments: Array<Fragment> = arrayOf(
        NowPlayingFragment(),
        UpcomingFragment(),
        PopularMoviesFragment(),
        TopRatedMoviesFragment()
    )

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

}