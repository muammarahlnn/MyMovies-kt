package com.ardnn.mymovies.activities

import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ardnn.mymovies.R
import com.ardnn.mymovies.databinding.ActivityMainBinding
import com.ardnn.mymovies.fragments.favorite.FavoriteFragment
import com.ardnn.mymovies.fragments.movies.MoviesFragment
import com.ardnn.mymovies.fragments.recent.RecentFragment
import com.ardnn.mymovies.fragments.tvshows.TvShowsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    companion object {
        private var navKey = 0
        fun setNavKey(navKey: Int) {
            this.navKey = navKey
        }
    }

    // view binding
    private lateinit var binding: ActivityMainBinding

    // toolbar
    private lateinit var toolbar: Toolbar
    private lateinit var tvSection: TextView
    private lateinit var ivIcon: ImageView

    // vars
    private val TIME_INTERVAL = 2000
    private var timeBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize widgets
        toolbar = findViewById(R.id.toolbar_main)
        tvSection = findViewById(R.id.tv_section_toolbar)
        ivIcon = findViewById(R.id.iv_icon_toolbar)


        // set action bar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // set bottom navigation view
        val bnvMain = binding.bnvNavigationMain
        bnvMain.setOnNavigationItemSelectedListener(this)
        bnvMain.itemIconTintList = null

        bnvMain.selectedItemId =  when (navKey) {
            1 -> R.id.item_movies_main
            2 -> R.id.item_tv_shows_main
            3 -> R.id.item_recent_main
            4 -> R.id.item_favorite_main
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

    override fun onBackPressed() {
        if (timeBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, "Press back button again to exit", Toast.LENGTH_SHORT).show()
        }
        timeBackPressed = System.currentTimeMillis()
    }

}