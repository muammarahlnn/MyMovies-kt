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
import com.ardnn.mymovies.models.MovieOutlineResponse
import com.ardnn.mymovies.networks.MoviesApiClient
import com.ardnn.mymovies.networks.MoviesApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NowPlayingFragment : Fragment(), OnItemClick<MovieOutline> {

    // recyclerview attr
    private lateinit var rvNowPlaying: RecyclerView
    private lateinit var moviesOutlineAdapter: MoviesOutlineAdapter
    private lateinit var movieOutlineList: List<MovieOutline>

    // widgets
    private lateinit var pbNowPlaying: ProgressBar
    private lateinit var srlNowPlaying: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_now_playing, container, false)

        // initialize widgets
        rvNowPlaying = view.findViewById(R.id.rv_now_playing)
        pbNowPlaying = view.findViewById(R.id.pb_now_playing)
        srlNowPlaying = view.findViewById(R.id.srl_now_playing)
        srlNowPlaying.setOnRefreshListener {
            loadData()
            srlNowPlaying.isRefreshing = false
        }

        // set recyclerview layout
        rvNowPlaying.layoutManager = GridLayoutManager(activity, 2)

        // load MoviesNowPlaying's data from TMDB API
        loadData()

        return view
    }

    private fun loadData() {
        val moviesApiInterface: MoviesApiInterface = MoviesApiClient.retrofit
            .create(MoviesApiInterface::class.java)

        val movieOutlineResponseCall: Call<MovieOutlineResponse> =
            moviesApiInterface.getNowPlayingMovies(Utils.API_KEY)
        movieOutlineResponseCall.enqueue(object : Callback<MovieOutlineResponse> {
            override fun onResponse(
                call: Call<MovieOutlineResponse>,
                response: Response<MovieOutlineResponse>
            ) {
                if (response.isSuccessful && response.body()?.movieOutlineList != null) {
                    // put MoviesNowPlaying's data to list
                    movieOutlineList = response.body()!!.movieOutlineList!!

                    // set recyclerview adapter
                    moviesOutlineAdapter = MoviesOutlineAdapter(movieOutlineList, this@NowPlayingFragment)
                    rvNowPlaying.adapter = moviesOutlineAdapter
                } else {
                    Toast.makeText(activity, "Response failed.", Toast.LENGTH_SHORT).show()
                }

                // remove progress bar
                pbNowPlaying.visibility = View.GONE
            }

            override fun onFailure(call: Call<MovieOutlineResponse>, t: Throwable) {
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