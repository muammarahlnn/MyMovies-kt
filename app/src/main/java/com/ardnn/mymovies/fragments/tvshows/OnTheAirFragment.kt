package com.ardnn.mymovies.fragments.tvshows

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
import com.ardnn.mymovies.activities.TvShowDetailActivity
import com.ardnn.mymovies.adapters.OnItemClick
import com.ardnn.mymovies.adapters.TvShowsOutlineAdapter
import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.Genre
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.models.TvShowOutline
import com.ardnn.mymovies.api.repositories.TvShowRepository
import com.ardnn.mymovies.api.callbacks.tvshows.OnTheAirTvShowsCallback

class OnTheAirFragment : Fragment(), OnItemClick, SwipeRefreshLayout.OnRefreshListener {

    // recyclerview attr
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TvShowsOutlineAdapter

    // widgets
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_on_the_air, container, false)

        // initialize widgets
        recyclerView = view.findViewById(R.id.rv_on_the_air)
        progressBar = view.findViewById(R.id.pb_on_the_air)
        swipeRefresh = view.findViewById(R.id.srl_on_the_air)
        swipeRefresh.setOnRefreshListener(this)

        // set recyclerview layout
        recyclerView.layoutManager = GridLayoutManager(activity, 2)

        // load MoviesNowPlaying's data from TMDB API
        loadData()

        return view
    }

    private fun loadData() {
        TvShowRepository.getOnTheAirTvShows(object : OnTheAirTvShowsCallback {
            override fun onSuccess(onTheAirList: List<TvShowOutline>) {
                // setup recyclerview
                adapter = TvShowsOutlineAdapter(onTheAirList, this@OnTheAirFragment)
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

    override fun itemClicked(tvShowOutline: TvShowOutline) {
        // go to tv show detail
        val goToTvShowDetail = Intent(activity, TvShowDetailActivity::class.java)
        goToTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_ID, tvShowOutline.id)
        startActivity(goToTvShowDetail)
    }

    // do nothing
    override fun itemClicked(movieOutline: MovieOutline) {}
    override fun itemClicked(genre: Genre) {}
    override fun itemClicked(cast: Cast) {}

}