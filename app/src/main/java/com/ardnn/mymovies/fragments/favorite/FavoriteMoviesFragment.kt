package com.ardnn.mymovies.fragments.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.mymovies.activities.MainActivity
import com.ardnn.mymovies.activities.MovieDetailActivity
import com.ardnn.mymovies.adapters.FavoriteMoviesAdapter
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.database.viewmodels.FavoriteFilmViewModel
import com.ardnn.mymovies.databinding.FragmentFavoriteMoviesBinding
import com.ardnn.mymovies.listeners.SingleClickListener

class FavoriteMoviesFragment : Fragment(), SingleClickListener<FavoriteMovies> {

    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteFilmViewModel
    private lateinit var adapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)

        // initialization
        viewModel = ViewModelProvider(this).get(FavoriteFilmViewModel::class.java)
        binding.rvFavoriteMovies.layoutManager = LinearLayoutManager(activity)

        // get favorite movies from database
        viewModel.favoriteMovieList.observe(viewLifecycleOwner, { favoriteMovieList ->
            // set recyclerview adapter
            adapter = FavoriteMoviesAdapter(favoriteMovieList, this)
            binding.rvFavoriteMovies.adapter = adapter

            // check if favorite movies is not empty then show the alert text and vice versa
            binding.tvEmptyFavoriteMovies.visibility =
                if (favoriteMovieList.isEmpty()) View.VISIBLE else View.GONE
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(item: FavoriteMovies) {
        // set nav key
        MainActivity.setNavKey(4)

        // go to movie detail
        val goToMovieDetail = Intent(activity, MovieDetailActivity::class.java)
        goToMovieDetail.putExtra(MovieDetailActivity.EXTRA_ID, item.id)
        startActivity(goToMovieDetail)
    }

}