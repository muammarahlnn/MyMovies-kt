package com.ardnn.mymovies.fragments.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ardnn.mymovies.adapters.TvShowsPagerAdapter
import com.ardnn.mymovies.databinding.FragmentTvShowsBinding
import com.ardnn.mymovies.helpers.Utils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TvShowsFragment : Fragment() {

    // view binding
    private var _binding: FragmentTvShowsBinding? = null
    private val binding get() = _binding!!

    // adapters
    private lateinit var tvShowsPagerAdapter: TvShowsPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTvShowsBinding.inflate(inflater, container, false)

        // set viewpager
        tvShowsPagerAdapter = TvShowsPagerAdapter(activity)
        binding.vp2TvShows.adapter = tvShowsPagerAdapter
        binding.vp2TvShows.currentItem = 0

        // set tablayout
        TabLayoutMediator(binding.tlTvShows, binding.vp2TvShows) { tab: TabLayout.Tab, position: Int ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
        binding.tlTvShows.getTabAt(0)?.text = "Airing Today"
        binding.tlTvShows.getTabAt(1)?.text = "On The Air"
        binding.tlTvShows.getTabAt(2)?.text = "Popular"
        binding.tlTvShows.getTabAt(3)?.text = "Top Rated"
        Utils.equalingEachTabWidth(binding.tlTvShows) // to allow equal width for each tab, while (TabLayout.MODE_SCROLLABLE)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}