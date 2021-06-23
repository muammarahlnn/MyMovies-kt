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
import com.ardnn.mymovies.networks.TvShowsApiClient
import com.ardnn.mymovies.networks.TvShowsApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnTheAirFragment : Fragment(), OnItemClick<TvShowsOutline> {

    // recyclerview attr
    private lateinit var rvOnTheAir: RecyclerView
    private lateinit var tvShowsOutlineAdapter: TvShowsOutlineAdapter
    private lateinit var tvShowsOutlineList: List<TvShowsOutline>

    // widgets
    private lateinit var pbOnTheAir: ProgressBar
    private lateinit var srlOnTheAir: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_on_the_air, container, false)

        // initialize widgets
        rvOnTheAir = view.findViewById(R.id.rv_on_the_air)
        pbOnTheAir = view.findViewById(R.id.pb_on_the_air)
        srlOnTheAir = view.findViewById(R.id.srl_on_the_air)
        srlOnTheAir.setOnRefreshListener {
            loadData()
            srlOnTheAir.isRefreshing = false
        }

        // set recyclerview layout
        rvOnTheAir.layoutManager = GridLayoutManager(activity, 2)

        // load MoviesNowPlaying's data from TMDB API
        loadData()

        return view
    }

    private fun loadData() {
        val tvShowsApiInterface: TvShowsApiInterface = TvShowsApiClient.retrofit
            .create(TvShowsApiInterface::class.java)

        val tvShowsOutlineCall: Call<TvShowsOutline> =
            tvShowsApiInterface.getOnTheAirTvShows(Utils.API_KEY)
        tvShowsOutlineCall.enqueue(object : Callback<TvShowsOutline> {
            override fun onResponse(
                call: Call<TvShowsOutline>,
                response: Response<TvShowsOutline>
            ) {
                if (response.isSuccessful && response.body()?.tvShowsOutlineList != null) {
                    // put data to list
                    tvShowsOutlineList = response.body()!!.tvShowsOutlineList

                    // set recyclerview
                    tvShowsOutlineAdapter = TvShowsOutlineAdapter(tvShowsOutlineList, this@OnTheAirFragment)
                    rvOnTheAir.adapter = tvShowsOutlineAdapter
                } else {
                    Toast.makeText(activity, "Response failed.", Toast.LENGTH_SHORT).show()
                }

                // remove progress bar
                pbOnTheAir.visibility = View.GONE
            }

            override fun onFailure(call: Call<TvShowsOutline>, t: Throwable) {
                Toast.makeText(activity, "Response failure.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun itemClicked(data: TvShowsOutline) {
        Toast.makeText(activity, "You clicked ${data.title}", Toast.LENGTH_SHORT).show()
    }
}