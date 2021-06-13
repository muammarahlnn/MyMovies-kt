package com.ardnn.mymovies.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.mymovies.fragments.MoviesFragment
import com.ardnn.mymovies.fragments.TvShowsFragment

class HomeAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            MoviesFragment()
        } else {
            TvShowsFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

}