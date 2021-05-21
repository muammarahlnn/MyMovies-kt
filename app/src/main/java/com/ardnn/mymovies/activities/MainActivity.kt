package com.ardnn.mymovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.ardnn.mymovies.R
import androidx.fragment.app.Fragment
import com.ardnn.mymovies.fragments.FavoriteFragment
import com.ardnn.mymovies.fragments.HomeFragment
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

        // if button clicked
        bnvNavigation.setOnNavigationItemSelectedListener(this)
        bnvNavigation.selectedItemId = R.id.item_home_main
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.item_home_main -> {
                tvTitle.text = resources.getString(R.string.app_name)
                fragment = HomeFragment()
            }
            R.id.item_recent_main -> {
                tvTitle.text = resources.getString(R.string.recent)
                fragment = RecentFragment()
            }
            R.id.item_favorite_main -> {
                tvTitle.text = resources.getString(R.string.favorite)
                fragment = FavoriteFragment()
            }
        }

        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_content_main, fragment)
                .commit()
        }

        return true
    }

}