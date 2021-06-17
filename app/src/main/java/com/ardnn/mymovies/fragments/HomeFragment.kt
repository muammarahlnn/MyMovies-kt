package com.ardnn.mymovies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.MoviesPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    // viewpager2 attr
    private lateinit var moviesPagerAdapter: MoviesPagerAdapter
    private lateinit var tlMovies: TabLayout
    private lateinit var moviesPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        // set viewpager
        moviesPagerAdapter = MoviesPagerAdapter(activity)
        moviesPager = view.findViewById(R.id.vp2_home)
        moviesPager.adapter = moviesPagerAdapter
        moviesPager.currentItem = 0

        // set tablayout
        tlMovies = view.findViewById(R.id.tl_home)
        TabLayoutMediator(tlMovies, moviesPager) { tab, position ->
            run {
                tab.text = "OBJECT ${(position + 1)}"
            }
        }.attach()
        tlMovies.getTabAt(0)?.text = "Movies"
        tlMovies.getTabAt(1)?.text = "TV Shows"


        return view
    }
}