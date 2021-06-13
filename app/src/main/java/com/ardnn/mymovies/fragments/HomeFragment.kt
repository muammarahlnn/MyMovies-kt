package com.ardnn.mymovies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.HomeAdapter
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    // classes
    private lateinit var homeAdapter: HomeAdapter

    // widgets
    private lateinit var tlHome: TabLayout
    private lateinit var vp2Home: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        // initialize widgets
        tlHome = view.findViewById(R.id.tl_home)
        vp2Home = view.findViewById(R.id.vp2_home)

        // set home adapter
        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        if (fragmentManager != null) {
            homeAdapter = HomeAdapter(fragmentManager, lifecycle)
            vp2Home.adapter = homeAdapter
        }

        // add tablayout's tab
        tlHome.addTab(tlHome.newTab().setText("Movies"))
        tlHome.addTab(tlHome.newTab().setText("Tv Shows"))
        tlHome.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    vp2Home.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // do nothing
            }

        })

        // if pager slided
        vp2Home.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                tlHome.selectTab(tlHome.getTabAt(position))
            }
        })

        return view
    }
}