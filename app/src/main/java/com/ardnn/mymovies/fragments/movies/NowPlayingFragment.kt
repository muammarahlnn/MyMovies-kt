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
import com.ardnn.mymovies.activities.DetailActivity
import com.ardnn.mymovies.adapters.NowPlayingAdapter
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.MoviesNowPlaying
import com.ardnn.mymovies.models.MoviesNowPlayingResponse
import com.ardnn.mymovies.networks.MoviesNowPlayingApiClient
import com.ardnn.mymovies.networks.MoviesNowPlayingApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NowPlayingFragment : Fragment(), NowPlayingAdapter.OnItemClick {

    // classes
    private lateinit var nowPlayingAdapter: NowPlayingAdapter

    // widgets
    private lateinit var rvNowPlaying: RecyclerView
    private lateinit var pbNowPlaying: ProgressBar
    private lateinit var srlNowPlaying: SwipeRefreshLayout

    // attributes
    private lateinit var nowPlayingList: List<MoviesNowPlaying>

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
        val moviesNowPlayingApiInterface: MoviesNowPlayingApiInterface = MoviesNowPlayingApiClient.retrofit
            .create(MoviesNowPlayingApiInterface::class.java)

        val moviesNowPlayingResponseCall: Call<MoviesNowPlayingResponse> =
            moviesNowPlayingApiInterface.getMoviesNowPlaying(Utils.API_KEY)
        moviesNowPlayingResponseCall.enqueue(object : Callback<MoviesNowPlayingResponse> {
            override fun onResponse(
                call: Call<MoviesNowPlayingResponse>,
                response: Response<MoviesNowPlayingResponse>
            ) {
                if (response.isSuccessful && response.body()?.moviesNowPlayingList != null) {
                    // put MoviesNowPlaying's data to list
                    nowPlayingList = response.body()!!.moviesNowPlayingList!!

                    // set recyclerview adapter
                    nowPlayingAdapter = NowPlayingAdapter(nowPlayingList, this@NowPlayingFragment)
                    rvNowPlaying.adapter = nowPlayingAdapter
                } else {
                    Toast.makeText(activity, "Response failed.", Toast.LENGTH_SHORT).show()
                }

                // remove progress bar
                pbNowPlaying.visibility = View.GONE
            }

            override fun onFailure(call: Call<MoviesNowPlayingResponse>, t: Throwable) {
                Toast.makeText(activity, "Response failed.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onClick(position: Int) {
        // move to detail activity
        val goToDetail = Intent(activity, DetailActivity::class.java)
        goToDetail.putExtra(DetailActivity.EXTRA_MOVIE, nowPlayingList[position])
        startActivity(goToDetail)
    }


}