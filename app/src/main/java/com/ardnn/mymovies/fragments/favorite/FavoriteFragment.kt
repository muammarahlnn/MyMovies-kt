package com.ardnn.mymovies.fragments.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.mymovies.R
import com.ardnn.mymovies.activities.MainActivity
import com.ardnn.mymovies.activities.MovieDetailActivity
import com.ardnn.mymovies.activities.TvShowDetailActivity
import com.ardnn.mymovies.adapters.FavoriteMoviesAdapter
import com.ardnn.mymovies.adapters.FavoriteTvShowsAdapter
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.database.entities.FavoriteTvShows
import com.ardnn.mymovies.database.viewmodels.FavoriteFilmViewModel
import com.ardnn.mymovies.databinding.FragmentFavoriteBinding
import com.ardnn.mymovies.listeners.FavoriteClickListener
import com.ardnn.mymovies.listeners.SingleClickListener
import com.ardnn.mymovies.models.Movie

class FavoriteFragment : Fragment(), FavoriteClickListener {

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoriteFilmViewModel
    private var section = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // get section page
        section = arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 0

        // set recyclerview layout
        binding.rvFavorite.layoutManager = LinearLayoutManager(activity)

        // get favorite ada depends on its page
        viewModel = ViewModelProvider(this).get(FavoriteFilmViewModel::class.java)
        when (section) {
            0 ->  { // favorite movies
                viewModel.favoriteMovieList.observe(viewLifecycleOwner, { favoriteMovieList ->
                    val adapter = FavoriteMoviesAdapter(favoriteMovieList, this)
                    with (binding) {
                        // set recyclerview
                        rvFavorite.adapter = adapter

                        // check if favorite movies is empty then show alert to user and vice versa
                        if (favoriteMovieList.isEmpty()) {
                            tvEmptyFavorite.text = resources.getString(R.string.empty_favorite_movies)
                            tvEmptyFavorite.visibility = View.VISIBLE
                        } else {
                            tvEmptyFavorite.visibility = View.GONE
                        }
                    }
                })
            }
            1 -> { // favorite tv shows
                viewModel.favoriteTvShowList.observe(viewLifecycleOwner, { favoriteTvShowList ->
                    val adapter = FavoriteTvShowsAdapter(favoriteTvShowList, this)
                    with (binding) {
                        // set recyclerview
                        rvFavorite.adapter = adapter

                        // check if favorite tv shows is empty then show alert to user and vice versa
                        if (favoriteTvShowList.isEmpty()) {
                            tvEmptyFavorite.text = resources.getString(R.string.empty_favorite_tv_shows)
                            tvEmptyFavorite.visibility = View.VISIBLE
                        } else {
                            tvEmptyFavorite.visibility = View.GONE
                        }
                    }

                })
            }
            else -> {
                // show alert to user
                // that error occurred
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(favoriteMovie: FavoriteMovies) {
        // set nav key
        MainActivity.setNavKey(4)

        // to movie detail
        val toMovieDetail = Intent(activity, MovieDetailActivity::class.java)
        toMovieDetail.putExtra(MovieDetailActivity.EXTRA_ID, favoriteMovie.id)
        startActivity(toMovieDetail)
    }

    override fun onItemClicked(favoriteTvShow: FavoriteTvShows) {
        // set nav key
        MainActivity.setNavKey(4)

        // to tv show detail
        val toTvShowDetail = Intent(activity, TvShowDetailActivity::class.java)
        toTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_ID, favoriteTvShow.id)
        startActivity(toTvShowDetail)
    }


}