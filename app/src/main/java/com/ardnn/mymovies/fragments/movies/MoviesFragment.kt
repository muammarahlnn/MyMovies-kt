package com.ardnn.mymovies.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ardnn.mymovies.adapters.MoviesPagerAdapter
import com.ardnn.mymovies.databinding.FragmentMoviesBinding
import com.ardnn.mymovies.helpers.Utils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MoviesFragment : Fragment() {

    // view binding
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    // adapters
    private lateinit var moviesPagerAdapter: MoviesPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        // set viewpager
        moviesPagerAdapter = MoviesPagerAdapter(activity)
        binding.vp2Movies.adapter = moviesPagerAdapter
        binding.vp2Movies.currentItem = 0

        // set tablayout
        TabLayoutMediator(binding.tlMovies, binding.vp2Movies) { tab: TabLayout.Tab, position: Int ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
        binding.tlMovies.getTabAt(0)?.text = "Now Playing"
        binding.tlMovies.getTabAt(1)?.text = "Upcoming"
        binding.tlMovies.getTabAt(2)?.text = "Popular"
        binding.tlMovies.getTabAt(3)?.text = "Top Rated"
        Utils.equalingEachTabWidth(binding.tlMovies) // to allow equal width for each tab, while (TabLayout.MODE_SCROLLABLE)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}