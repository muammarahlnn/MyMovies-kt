package com.ardnn.mymovies.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.mymovies.fragments.favorite.FavoriteMoviesFragment
import com.ardnn.mymovies.fragments.favorite.FavoriteTvShowsFragment

class FavoritePagerAdapter(fragmentActivity: FragmentActivity?)
    : FragmentStateAdapter(fragmentActivity!!) {

    private val fragments: Array<Fragment> = arrayOf(
        FavoriteMoviesFragment(),
        FavoriteTvShowsFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}