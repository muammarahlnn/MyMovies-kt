package com.ardnn.mymovies.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ardnn.mymovies.adapters.MoviesPagerAdapter
import com.ardnn.mymovies.databinding.FragmentMoviesPagerBinding
import com.ardnn.mymovies.helpers.Utils
import com.google.android.material.tabs.TabLayoutMediator


class MoviesPagerFragment : Fragment() {

    companion object {
        val tabTitles = arrayOf(
            "Now Playing",
            "Upcoming",
            "Popular",
            "Top Rated"
        )
    }

    // view binding
    private var _binding: FragmentMoviesPagerBinding? = null
    private val binding get() = _binding!!

    // adapters
    private lateinit var moviesPagerAdapter: MoviesPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesPagerBinding.inflate(inflater, container, false)

        // set viewpager
        val moviesRootPagerAdapter = MoviesPagerAdapter(activity)
        binding.vp2Movies.adapter = moviesRootPagerAdapter

        // set tab layout
        TabLayoutMediator(binding.tlMovies, binding.vp2Movies) { tab, pos ->
            tab.text = tabTitles[pos]
        }.attach()

        // to allow equal width for each tab, while (TabLayout.MODE_SCROLLABLE)
        Utils.equalingEachTabWidth(binding.tlMovies)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}