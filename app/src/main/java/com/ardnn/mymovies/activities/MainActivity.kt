package com.ardnn.mymovies.activities

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
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
    companion object {
        const val EXTRA_NAV_KEY = "extra_nav_key"
    }

    // widgets
    private lateinit var toolbar: Toolbar
    private lateinit var tvSection: TextView
    private lateinit var ivIcon: ImageView
    private lateinit var bnvMain: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize widgets
        toolbar = findViewById(R.id.toolbar_main)
        tvSection = findViewById(R.id.tv_section_toolbar)
        ivIcon = findViewById(R.id.iv_icon_toolbar)

        // set action bar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // set bottom navigation view
        bnvMain = findViewById(R.id.bnv_navigation_main)
        bnvMain.setOnNavigationItemSelectedListener(this)
        bnvMain.itemIconTintList = null

        val navKey: Int = intent.getIntExtra(EXTRA_NAV_KEY, 0)
        bnvMain.selectedItemId =  when (navKey) {
            1 -> R.id.item_movies_main
            2 -> R.id.item_tv_shows_main
            else -> R.id.item_movies_main
        }

        // check if keyboard shows up
        val viewRoot: View = findViewById(R.id.activity_root)
        viewRoot.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            viewRoot.getWindowVisibleDisplayFrame(rect)

            // if keyboard shows up then hide bnv and vice versa
            val heightDiff: Int = viewRoot.rootView.height - rect.height()
            bnvMain.visibility =
                if (heightDiff > 0.25 * viewRoot.rootView.height) View.GONE
                else View.VISIBLE
        }
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