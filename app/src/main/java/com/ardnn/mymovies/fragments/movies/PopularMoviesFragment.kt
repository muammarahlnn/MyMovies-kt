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
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.networks.MoviesApiClient
import com.ardnn.mymovies.networks.MoviesApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularMoviesFragment : Fragment(), OnItemClick<MovieOutline> {

    // recyclerview attr
    private lateinit var rvPopular: RecyclerView
    private lateinit var moviesOutlineAdapter: MoviesOutlineAdapter
    private lateinit var movieOutlineList: List<MovieOutline>

    // widgets
    private lateinit var pbPopular: ProgressBar
    private lateinit var srlPopular: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_popular_movies, container, false)

        // initialize widgets
        rvPopular = view.findViewById(R.id.rv_popular_movies)
        pbPopular = view.findViewById(R.id.pb_popular_movies)
        srlPopular = view.findViewById(R.id.srl_popular_movies)
        srlPopular.setOnRefreshListener {
            loadData()
            srlPopular.isRefreshing = false
        }

        // set recyclerview layout
        rvPopular.layoutManager = GridLayoutManager(activity, 2)

        // load MoviesNowPlaying's data from TMDB API
        loadData()

        return view
    }

    private fun loadData() {

        val moviesApiInterface: MoviesApiInterface = MoviesApiClient.retrofit
            .create(MoviesApiInterface::class.java)

        val movieOutlineCall: Call<MovieOutline> =
            moviesApiInterface.getPopularMovies(Utils.API_KEY)
        movieOutlineCall.enqueue(object : Callback<MovieOutline> {
            override fun onResponse(
                call: Call<MovieOutline>,
                response: Response<MovieOutline>
            ) {
                if (response.isSuccessful && response.body()?.movieOutlineList != null) {
                    // put MoviesNowPlaying's data to list
                    movieOutlineList = response.body()!!.movieOutlineList

                    // set recyclerview adapter
                    moviesOutlineAdapter = MoviesOutlineAdapter(movieOutlineList, this@PopularMoviesFragment)
                    rvPopular.adapter = moviesOutlineAdapter
                } else {
                    Toast.makeText(activity, "Response failed.", Toast.LENGTH_SHORT).show()
                }

                // remove progress bar
                pbPopular.visibility = View.GONE
            }

            override fun onFailure(call: Call<MovieOutline>, t: Throwable) {
                Toast.makeText(activity, "Response failed.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun itemClicked(data: MovieOutline) {
        // go to movies detail
        val goToMovieDetail = Intent(activity, MovieDetailActivity::class.java)
        goToMovieDetail.putExtra(MovieDetailActivity.EXTRA_ID, data.id)
        startActivity(goToMovieDetail)
    }

}