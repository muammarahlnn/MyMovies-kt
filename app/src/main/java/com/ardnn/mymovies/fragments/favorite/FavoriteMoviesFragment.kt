package com.ardnn.mymovies.fragments.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.activities.MainActivity
import com.ardnn.mymovies.activities.MovieDetailActivity
import com.ardnn.mymovies.adapters.FavoriteMoviesAdapter
import com.ardnn.mymovies.database.FavoriteFilmViewModel
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.listeners.SingleClickListener

class FavoriteMoviesFragment : Fragment(), SingleClickListener<FavoriteMovies> {

    // recyclerview favorite movies
    private lateinit var viewModel: FavoriteFilmViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteMoviesAdapter

    // widgets
    private lateinit var tvEmpty: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite_movies, container, false)

        // initialization
        viewModel = ViewModelProvider(this).get(FavoriteFilmViewModel::class.java)
        tvEmpty = view.findViewById(R.id.tv_empty_favorite_movies)
        recyclerView = view.findViewById(R.id.rv_favorite_movies)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // get favorite movies from database
        viewModel.favoriteMovieList.observe(viewLifecycleOwner, { favoriteMovieList ->
            // set recyclerview adapter
            adapter = FavoriteMoviesAdapter(favoriteMovieList, this)
            recyclerView.adapter = adapter

            // check if favorite movies is not empty then remove the alert text and vice versa
            tvEmpty.visibility = if (favoriteMovieList.isNotEmpty()) View.GONE else View.VISIBLE
        })

        return view
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