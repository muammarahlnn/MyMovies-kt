package com.ardnn.mymovies.fragments.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.FavoritePagerAdapter
import com.ardnn.mymovies.database.FavoriteFilmViewModel
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.database.entities.FavoriteTvShows
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
    private var isFavoriteMovieListEmpty: Boolean = false
    private var isFavoriteTvShowListEmpty: Boolean = false
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
                isFavoriteMovieListEmpty = true
            }
        })
        viewModel.favoriteTvShowList.observe(viewLifecycleOwner, { favoriteTvShowList ->
            if (favoriteTvShowList.isEmpty()) {
                isFavoriteTvShowListEmpty = true
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
                0 -> {
                    deleteAllFavoriteMovies()
                }
                1 -> deleteAllFavoriteTvShows()
                -1 -> {
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

    private fun deleteAllFavoriteMovies() {
        // check if favorite movies is already empty
        if (isFavoriteMovieListEmpty) {
            Toast.makeText(
                activity,
                "Your favorite movies is already empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // create an alert
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure want to clear your favorite movies?")
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAllMovies()
            Toast.makeText(
                activity,
                "Favorite Movies successfully cleared",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No", null)
        builder.create().show()
    }

    private fun deleteAllFavoriteTvShows() {
        // check if favorite tv shows is already empty
        if (isFavoriteTvShowListEmpty) {
            Toast.makeText(
                activity,
                "Your favorite tv shows is already empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure want to clear your favorite tv shows?")
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteALlTvShows()
            Toast.makeText(
                activity,
                "Favorite TV Shows successfully cleared",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No", null)
        builder.create().show()
    }
}