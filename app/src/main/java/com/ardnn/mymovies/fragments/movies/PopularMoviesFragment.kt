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
import com.ardnn.mymovies.api.callbacks.movies.PopularMoviesCallback

class PopularMoviesFragment : Fragment(), OnItemClick, SwipeRefreshLayout.OnRefreshListener {

    // recyclerview attr
    private lateinit var recyclerView: RecyclerView
    private var adapter: MoviesOutlineAdapter? = null

    // widgets
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    // variables
    private var currentPage: Int = 1
    private var isFetching: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_popular_movies, container, false)

        // initialize widgets
        progressBar = view.findViewById(R.id.pb_popular_movies)

        // set swipe refresh layout
        swipeRefresh = view.findViewById(R.id.srl_popular_movies)
        swipeRefresh.setOnRefreshListener(this)

        // set recyclerview
        recyclerView = view.findViewById(R.id.rv_popular_movies)
        setRecyclerView()

        // load MoviesNowPlaying's data from TMDB API
        loadData(currentPage)

        return view
    }

    private fun loadData(page: Int) {
        MovieRepository.getPopularMovies(page, object : PopularMoviesCallback {
            override fun onSuccess(popularMoviesList: MutableList<MovieOutline>) {
                if (adapter == null) {
                    // setup recyclerview
                    adapter = MoviesOutlineAdapter(popularMoviesList, this@PopularMoviesFragment)
                    adapter?.notifyDataSetChanged()
                    recyclerView.adapter = adapter
                } else {
                    // append adapter list with new list from next page
                    adapter?.appendList(popularMoviesList)
                }

                // done fetching
                progressBar.visibility = View.GONE
                currentPage = page
                isFetching = false
                swipeRefresh.isRefreshing = false
            }

            override fun onFailure(message: String) {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setRecyclerView() {
        // set recyclerview layout
        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = layoutManager

        // listener if recyclerview reached last item then fetch next page
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItem: Int = layoutManager.itemCount
                val visibleItem: Int = layoutManager.childCount
                val firstVisibleItem: Int = layoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItem >= totalItem / 2) {
                    if (!isFetching) {
                        isFetching = true
                        loadData(++currentPage)
                        isFetching = false
                    }
                }

            }
        })
    }

    override fun onRefresh() {
        // reset adapter
        adapter = null
        currentPage = 1
        progressBar.visibility = View.VISIBLE
        loadData(currentPage)
    }

    override fun itemClicked(movieOutline: MovieOutline) {
        // go to movies detail
        val goToMovieDetail = Intent(activity, MovieDetailActivity::class.java)
        goToMovieDetail.putExtra(MovieDetailActivity.EXTRA_ID, movieOutline.id)
        startActivity(goToMovieDetail)
    }

    override fun itemClicked(tvShowOutline: TvShowOutline) {}
    override fun itemClicked(genre: Genre) {}
    override fun itemClicked(cast: Cast) {}

}