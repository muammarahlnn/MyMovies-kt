package com.ardnn.mymovies.fragments.movies

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ardnn.mymovies.R
import com.ardnn.mymovies.activities.MovieDetailActivity
import com.ardnn.mymovies.adapters.MoviesOutlineAdapter
import com.ardnn.mymovies.adapters.OnItemClick
import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.Genre
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.models.TvShowOutline
import com.ardnn.mymovies.api.repositories.MovieRepository
import com.ardnn.mymovies.api.callbacks.movies.NowPlayingMoviesCallback

class NowPlayingFragment : Fragment(), OnItemClick, SwipeRefreshLayout.OnRefreshListener {

    // recyclerview attr
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoviesOutlineAdapter

    // widgets
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_now_playing, container, false)

        // initialize widgets
        recyclerView = view.findViewById(R.id.rv_now_playing)
        progressBar = view.findViewById(R.id.pb_now_playing)
        swipeRefresh = view.findViewById(R.id.srl_now_playing)
        swipeRefresh.setOnRefreshListener(this)

        // set recyclerview layout
        recyclerView.layoutManager = GridLayoutManager(activity, 2)

        // load MoviesNowPlaying's data from TMDB API
        loadData()

        return view
    }

    private fun loadData() {
        MovieRepository.getNowPlayingMovies(object : NowPlayingMoviesCallback {
            override fun onSuccess(nowPlayingList: List<MovieOutline>) {
                // setup recyclerview
                adapter = MoviesOutlineAdapter(nowPlayingList, this@NowPlayingFragment)
                recyclerView.adapter = adapter

                // remove progress bar
                progressBar.visibility = View.GONE
            }

            override fun onFailure(message: String) {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onRefresh() {
        loadData()
        swipeRefresh.isRefreshing = false
    }

    override fun itemClicked(movieOutline: MovieOutline) {
        // go to movie detail
        val goToMovieDetail = Intent(activity, MovieDetailActivity::class.java)
        goToMovieDetail.putExtra(MovieDetailActivity.EXTRA_ID, movieOutline.id)
        startActivity(goToMovieDetail)
    }

    // do nothing
    override fun itemClicked(tvShowOutline: TvShowOutline) {}
    override fun itemClicked(genre: Genre) {}
    override fun itemClicked(cast: Cast) {}
}