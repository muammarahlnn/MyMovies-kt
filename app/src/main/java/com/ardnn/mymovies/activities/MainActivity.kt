package com.ardnn.mymovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.ardnn.mymovies.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ardnn.mymovies.fragments.FavoriteFragment
import com.ardnn.mymovies.fragments.MoviesFragment
import com.ardnn.mymovies.fragments.RecentFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    // widgets
    private lateinit var bnvNavigation: BottomNavigationView
    private lateinit var tvTitle: TextView

    // fragments
    private val fragmentMovies: Fragment = MoviesFragment()
    private val fragmentRecent: Fragment = RecentFragment()
    private val fragmentFavorite: Fragment = FavoriteFragment()
    private val fragmentManager: FragmentManager = supportFragmentManager
    private var fragmentActive: Fragment = fragmentMovies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize widgets
        bnvNavigation = findViewById(R.id.bnv_navigation_main)
        tvTitle = findViewById(R.id.tv_title_main)

        // add fragments to fragment manager
        addFragmentsToFragmentManager()

        // if navigation clicked
        bnvNavigation.setOnNavigationItemSelectedListener(this)
    }

    private fun addFragmentsToFragmentManager() {
        // add main fragments and hide other fragments
        fragmentManager.beginTransaction()
            .add(R.id.fl_content_main, fragmentMovies)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fl_content_main, fragmentRecent)
            .hide(fragmentRecent)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fl_content_main, fragmentFavorite)
            .hide(fragmentFavorite)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_home_main -> {
                tvTitle.text = resources.getString(R.string.app_name)
                fragmentManager.beginTransaction()
                    .hide(fragmentActive)
                    .show(fragmentMovies)
                    .commit()
                fragmentActive = fragmentMovies
                return true
            }
            R.id.item_recent_main -> {
                tvTitle.text = resources.getString(R.string.recent)
                fragmentManager.beginTransaction()
                    .hide(fragmentActive)
                    .show(fragmentRecent)
                    .commit()
                fragmentActive = fragmentRecent
                return true
            }
            R.id.item_favorite_main -> {
                tvTitle.text = resources.getString(R.string.favorite)
                fragmentManager.beginTransaction()
                    .hide(fragmentActive)
                    .show(fragmentFavorite)
                    .commit()
                fragmentActive = fragmentFavorite
                return true
            }
        }

        return false
    }

}