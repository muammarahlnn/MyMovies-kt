package com.ardnn.mymovies.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.MoviesPagerAdapter
import com.ardnn.mymovies.helpers.Utils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MoviesFragment : Fragment() {

    // viewpager2 attr
    private lateinit var moviesPagerAdapter: MoviesPagerAdapter
    private lateinit var tlMovies: TabLayout
    private lateinit var moviesPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_movies, container, false)

        // set viewpager
        moviesPagerAdapter = MoviesPagerAdapter(activity)
        moviesPager = view.findViewById(R.id.vp2_movies)
        moviesPager.adapter = moviesPagerAdapter
        moviesPager.currentItem = 0

        // set tablayout
        tlMovies = view.findViewById(R.id.tl_movies)
        TabLayoutMediator(tlMovies, moviesPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
        tlMovies.getTabAt(0)?.text = "Now Playing"
        tlMovies.getTabAt(1)?.text = "Upcoming"
        tlMovies.getTabAt(2)?.text = "Popular"
        tlMovies.getTabAt(3)?.text = "Top Rated"
        Utils.equalingEachTabWidth(tlMovies) // to allow equal width for each tab, while (TabLayout.MODE_SCROLLABLE)

        return view
    }


}