package com.ardnn.mymovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.ardnn.mymovies.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ardnn.mymovies.fragments.FavoriteFragment
import com.ardnn.mymovies.fragments.HomeFragment
import com.ardnn.mymovies.fragments.MoviesFragment
import com.ardnn.mymovies.fragments.RecentFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    // widgets
    private lateinit var bnvNavigation: BottomNavigationView
    private lateinit var tvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize widgets
        bnvNavigation = findViewById(R.id.bnv_navigation_main)
        tvTitle = findViewById(R.id.tv_title_main)

        // if navigation clicked
        bnvNavigation.setOnNavigationItemSelectedListener(this)
        bnvNavigation.itemIconTintList = null
        bnvNavigation.selectedItemId = R.id.item_movies_main
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.item_movies_main -> {
                tvTitle.text = resources.getString(R.string.app_name)
                selectedFragment = HomeFragment()
            }
            R.id.item_recent_main -> {
                tvTitle.text = resources.getString(R.string.recent)
                selectedFragment = RecentFragment()
            }
            R.id.item_favorite_main -> {
                tvTitle.text = resources.getString(R.string.favorite)
                selectedFragment = FavoriteFragment()
            }
        }

        if (selectedFragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_content_main, selectedFragment)
                .commit()

            return true
        }

//        var tempFragment: Fragment? = null
//        when (item.itemId) {
//            R.id.item_movies_main -> {
//                tvTitle.text = resources.getString(R.string.app_name)
//                tempFragment = fragmentHome
//            }
//            R.id.item_recent_main -> {
//                tvTitle.text = resources.getString(R.string.recent)
//                tempFragment = fragmentRecent
//            }
//            R.id.item_favorite_main -> {
//                tvTitle.text = resources.getString(R.string.favorite)
//                tempFragment = fragmentFavorite
//            }
//        }
//
//        if (tempFragment != null) {
//            fragmentManager.beginTransaction()
//                .hide(fragmentActive)
//                .show(tempFragment)
//                .commit()
//            fragmentActive = tempFragment
//
//            return true
//        }

        return false
    }

}