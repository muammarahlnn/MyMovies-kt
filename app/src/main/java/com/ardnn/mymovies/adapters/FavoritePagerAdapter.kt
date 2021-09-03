package com.ardnn.mymovies.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.mymovies.fragments.favorite.FavoriteFragment
import com.ardnn.mymovies.fragments.favorite.FavoritePagerFragment

class FavoritePagerAdapter(fragmentActivity: FragmentActivity?) :
    FragmentStateAdapter(fragmentActivity!!) {

    override fun createFragment(position: Int): Fragment {
        return FavoriteFragment.newInstance(position)
    }

    override fun getItemCount(): Int {
        return FavoritePagerFragment.tabTitles.size
    }
}