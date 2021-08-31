package com.ardnn.mymovies.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.mymovies.fragments.movies.MoviesPagerFragment
import com.ardnn.mymovies.fragments.movies.MoviesFragment

class MoviesPagerAdapter(fragmentActivity: FragmentActivity?) :
    FragmentStateAdapter(fragmentActivity!!) {

    override fun createFragment(position: Int): Fragment {
        return MoviesFragment.newInstance(position)
    }

    override fun getItemCount(): Int {
        return MoviesPagerFragment.tabTitles.size
    }
}