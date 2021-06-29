package com.ardnn.mymovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.ardnn.mymovies.R
import androidx.fragment.app.Fragment
import com.ardnn.mymovies.fragments.FavoriteFragment
import com.ardnn.mymovies.fragments.movies.MoviesFragment
import com.ardnn.mymovies.fragments.RecentFragment
import com.ardnn.mymovies.fragments.tvshows.TvShowsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    // widgets
    private lateinit var toolbar: Toolbar
    private lateinit var tvSection: TextView
    private lateinit var ivIcon: ImageView
    private lateinit var bnvNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize widgets
        toolbar = findViewById(R.id.toolbar_main)
        tvSection = findViewById(R.id.tv_section_toolbar)
        ivIcon = findViewById(R.id.iv_icon_toolbar)

        // set bottom navigation view
        bnvNavigation = findViewById(R.id.bnv_navigation_main)
        bnvNavigation.setOnNavigationItemSelectedListener(this)
        bnvNavigation.itemIconTintList = null
        bnvNavigation.selectedItemId = R.id.item_movies_main
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.item_movies_main -> {
                tvSection.text = resources.getString(R.string.movies)
                ivIcon.setImageResource(R.drawable.ic_movies_yellow)
                selectedFragment = MoviesFragment()
            }
            R.id.item_tv_shows_main -> {
                tvSection.text = resources.getString(R.string.tv_shows)
                ivIcon.setImageResource(R.drawable.ic_tv_shows_yellow)
                selectedFragment = TvShowsFragment()
            }
            R.id.item_recent_main -> {
                tvSection.text = resources.getString(R.string.recent)
                ivIcon.setImageResource(R.drawable.ic_recent_yellow)
                selectedFragment = RecentFragment()
            }
            R.id.item_favorite_main -> {
                tvSection.text = resources.getString(R.string.favorite)
                ivIcon.setImageResource(R.drawable.ic_favorite_false)
                selectedFragment = FavoriteFragment()
            }
        }

        if (selectedFragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_content_main, selectedFragment)
                .commit()

            return true
        }

        return false
    }

}