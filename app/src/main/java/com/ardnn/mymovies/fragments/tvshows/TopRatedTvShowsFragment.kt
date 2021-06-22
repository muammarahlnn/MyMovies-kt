package com.ardnn.mymovies.fragments.tvshows

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
import com.ardnn.mymovies.adapters.OnItemClick
import com.ardnn.mymovies.adapters.TvShowsOutlineAdapter
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.TvShowsOutline
import com.ardnn.mymovies.models.TvShowsOutlineResponse
import com.ardnn.mymovies.networks.TvShowsOutlineApiClient
import com.ardnn.mymovies.networks.TvShowsOutlineApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopRatedTvShowsFragment : Fragment(), OnItemClick<TvShowsOutline> {

    // recyclerview attr
    private lateinit var rvTopRated: RecyclerView
    private lateinit var tvShowsOutlineAdapter: TvShowsOutlineAdapter
    private lateinit var tvShowsOutlineList: List<TvShowsOutline>

    // widgets
    private lateinit var pbTopRated: ProgressBar
    private lateinit var srlTopRated: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_top_rated_tv_shows, container, false)

        // initialize widgets
        rvTopRated = view.findViewById(R.id.rv_top_rated_tv_shows)
        pbTopRated = view.findViewById(R.id.pb_top_rated_tv_shows)
        srlTopRated = view.findViewById(R.id.srl_top_rated_tv_shows)
        srlTopRated.setOnRefreshListener {
            loadData()
            srlTopRated.isRefreshing = false
        }

        // set recyclerview layout
        rvTopRated.layoutManager = GridLayoutManager(activity, 2)

        // load MoviesNowPlaying's data from TMDB API
        loadData()

        return view
    }

    private fun loadData() {
        val tvShowsOutlineApiInterface: TvShowsOutlineApiInterface = TvShowsOutlineApiClient.retrofit
            .create(TvShowsOutlineApiInterface::class.java)

        val tvShowsOutlineResponseCall: Call<TvShowsOutlineResponse> =
            tvShowsOutlineApiInterface.getTopRatedTvShows(Utils.API_KEY)
        tvShowsOutlineResponseCall.enqueue(object : Callback<TvShowsOutlineResponse> {
            override fun onResponse(
                call: Call<TvShowsOutlineResponse>,
                response: Response<TvShowsOutlineResponse>
            ) {
                if (response.isSuccessful && response.body()?.tvShowsOutlineList != null) {
                    // put data to list
                    tvShowsOutlineList = response.body()!!.tvShowsOutlineList!!

                    // set recyclerview
                    tvShowsOutlineAdapter = TvShowsOutlineAdapter(tvShowsOutlineList, this@TopRatedTvShowsFragment)
                    rvTopRated.adapter = tvShowsOutlineAdapter
                } else {
                    Toast.makeText(activity, "Response failed.", Toast.LENGTH_SHORT).show()
                }

                // remove progress bar
                pbTopRated.visibility = View.GONE
            }

            override fun onFailure(call: Call<TvShowsOutlineResponse>, t: Throwable) {
                Toast.makeText(activity, "Response failure.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun itemClicked(data: TvShowsOutline) {
        Toast.makeText(activity, "You clicked ${data.title}", Toast.LENGTH_SHORT).show()
    }
}