package com.ardnn.mymovies.fragments.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.FavoritePagerAdapter
import com.ardnn.mymovies.helpers.Utils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {

    // viewpager attr
    private lateinit var favoritePagerAdapter: FavoritePagerAdapter
    private lateinit var tlFavorite: TabLayout
    private lateinit var favoritePager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        // set viewpager
        favoritePagerAdapter = FavoritePagerAdapter(activity)
        favoritePager = view.findViewById(R.id.vp2_favorite)
        favoritePager.adapter = favoritePagerAdapter
        favoritePager.currentItem = 0

        // set tablayout
        tlFavorite = view.findViewById(R.id.tl_favorite)
        TabLayoutMediator(tlFavorite, favoritePager) { tab: TabLayout.Tab, position: Int ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
        tlFavorite.getTabAt(0)?.text = "Movies"
        tlFavorite.getTabAt(1)?.text = "TV Shows"
        Utils.equalingEachTabWidth(tlFavorite) // to allow equal width for each tab, while (TabLayout.MODE_SCROLLABLE)

        return view
    }

}