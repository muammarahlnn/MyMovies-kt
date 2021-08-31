package com.ardnn.mymovies.fragments.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.TvShowsPagerAdapter
import com.ardnn.mymovies.databinding.FragmentTvShowsPagerBinding
import com.ardnn.mymovies.fragments.movies.MoviesPagerFragment
import com.ardnn.mymovies.helpers.Utils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TvShowsPagerFragment : Fragment() {

    companion object {
        val tabTitles = intArrayOf(
            R.string.airing_today,
            R.string.on_the_air,
            R.string.popular,
            R.string.top_rated,
        )
    }

    // view binding
    private var _binding: FragmentTvShowsPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTvShowsPagerBinding.inflate(inflater, container, false)

        // set viewpager
        val adapter = TvShowsPagerAdapter(activity)
        binding.vp2TvShows.adapter = adapter

        // set tab layout
        TabLayoutMediator(binding.tlTvShows, binding.vp2TvShows) { tab, pos ->
            tab.text = resources.getString(tabTitles[pos])
        }.attach()

        // to allow equal width for each tab, while (TabLayout.MODE_SCROLLABLE)
        Utils.equalingEachTabWidth(binding.tlTvShows)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}