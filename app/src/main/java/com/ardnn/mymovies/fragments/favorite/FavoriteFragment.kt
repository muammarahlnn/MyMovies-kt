package com.ardnn.mymovies.fragments.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.FavoritePagerAdapter
import com.ardnn.mymovies.database.viewmodels.FavoriteFilmViewModel
import com.ardnn.mymovies.helpers.Utils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {

    // viewpager attr
    private lateinit var favoritePagerAdapter: FavoritePagerAdapter
    private lateinit var tlFavorite: TabLayout
    private lateinit var favoritePager: ViewPager2

    // others
    private lateinit var viewModel: FavoriteFilmViewModel
    private val isListsEmpty = booleanArrayOf(
        false, // favorite movie list
        false // favorite tv show list
    )
    private var pos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        // initialize view model
        viewModel = ViewModelProvider(this).get(FavoriteFilmViewModel::class.java)

        // set viewpager
        favoritePagerAdapter = FavoritePagerAdapter(activity)
        favoritePager = view.findViewById(R.id.vp2_favorite)
        favoritePager.adapter = favoritePagerAdapter
        favoritePager.currentItem = 0

        // set tablayout
        tlFavorite = view.findViewById(R.id.tl_favorite)
        TabLayoutMediator(tlFavorite, favoritePager) { tab: TabLayout.Tab, position: Int ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
        tlFavorite.getTabAt(0)?.text = "Movies"
        tlFavorite.getTabAt(1)?.text = "TV Shows"
        Utils.equalingEachTabWidth(tlFavorite) // to allow equal width for each tab, while (TabLayout.MODE_SCROLLABLE)

        // check if favorites is empty or not
        viewModel.favoriteMovieList.observe(viewLifecycleOwner, { favoriteMovieList ->
            if (favoriteMovieList.isEmpty()) {
                isListsEmpty[0] = true
            }
        })
        viewModel.favoriteTvShowList.observe(viewLifecycleOwner, { favoriteTvShowList ->
            if (favoriteTvShowList.isEmpty()) {
                isListsEmpty[1] = true
            }
        })

        // get tab position
        tlFavorite.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pos = tab?.position ?: -1
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.item_toolbar_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.toolbar_item_delete) {
            when (pos) {
                0 -> deleteAllFavorites(0)
                1 -> deleteAllFavorites(1)
                else -> {
                    Toast.makeText(
                        activity,
                        "Can't cleared favorites currently due an error occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllFavorites(tabPosition: Int) {
        val flag: Boolean
        val favoritesType: String
        if (tabPosition == 0) {
            flag = isListsEmpty[0]
            favoritesType = "movies"

        } else {
            flag = isListsEmpty[1]
            favoritesType = "tv shows"
        }

        // check if favorites is already empty
        if (flag) {
            Toast.makeText(
                activity,
                "Your favorite $favoritesType is already empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // create an alert
        val alert = AlertDialog.Builder(requireContext())
        alert.setMessage("Are you sure want to clear your $favoritesType?")
        alert.setPositiveButton("Yes") { _, _ ->
            if (tabPosition == 0) viewModel.deleteAllMovies()
            else viewModel.deleteALlTvShows()

            Toast.makeText(
                activity,
                "Your favorite $favoritesType successfully cleared",
                Toast.LENGTH_SHORT
            ).show()
        }
        alert.setNegativeButton("No", null)
        alert.create().show()
    }
}