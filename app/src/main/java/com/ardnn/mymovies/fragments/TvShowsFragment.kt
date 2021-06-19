package com.ardnn.mymovies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.TvShowsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TvShowsFragment : Fragment() {

    // viewpager2 attr
    private lateinit var tvShowsPagerAdapter: TvShowsPagerAdapter
    private lateinit var tlTvShows: TabLayout
    private lateinit var tvShowsPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_tv_shows, container, false)

        // set viewpager
        tvShowsPagerAdapter = TvShowsPagerAdapter(activity)
        tvShowsPager = view.findViewById(R.id.vp2_tv_shows)
        tvShowsPager.adapter = tvShowsPagerAdapter
        tvShowsPager.currentItem = 0

        // set tablayout
        tlTvShows = view.findViewById(R.id.tl_tv_shows)
        TabLayoutMediator(tlTvShows, tvShowsPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
        tlTvShows.getTabAt(0)?.text = "Airing Today"
        tlTvShows.getTabAt(1)?.text = "On The Air"
        tlTvShows.getTabAt(2)?.text = "Popular"
        tlTvShows.getTabAt(3)?.text = "Top Rated"
        equalingEachTabWidth()

        return view
    }

    // to allow equal width for each tab, while (TabLayout.MODE_SCROLLABLE)
    private fun equalingEachTabWidth() {
        val slidingTab: ViewGroup = tlTvShows.getChildAt(0) as ViewGroup
        for (i in 0 until tlTvShows.tabCount) {
            val tab: View = slidingTab.getChildAt(i)
            val layoutParams: LinearLayout.LayoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 1F
            tab.layoutParams = layoutParams
        }
    }


}