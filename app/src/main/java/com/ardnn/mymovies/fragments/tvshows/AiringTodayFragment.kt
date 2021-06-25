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
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.Genre
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.models.TvShowsOutline
import com.ardnn.mymovies.networks.TvShowsApiClient
import com.ardnn.mymovies.networks.TvShowsApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AiringTodayFragment : Fragment(), OnItemClick {

    // recyclerview attr
    private lateinit var rvAiringToday: RecyclerView
    private lateinit var tvShowsOutlineAdapter: TvShowsOutlineAdapter
    private lateinit var tvShowsOutlineList: List<TvShowsOutline>

    // widgets
    private lateinit var pbAiringToday: ProgressBar
    private lateinit var srlAiringToday: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_now_playing, container, false)

        // initialize widgets
        rvAiringToday = view.findViewById(R.id.rv_now_playing)
        pbAiringToday = view.findViewById(R.id.pb_now_playing)
        srlAiringToday = view.findViewById(R.id.srl_now_playing)
        srlAiringToday.setOnRefreshListener {
            loadData()
            srlAiringToday.isRefreshing = false
        }

        // set recyclerview layout
        rvAiringToday.layoutManager = GridLayoutManager(activity, 2)

        // load MoviesNowPlaying's data from TMDB API
        loadData()

        return view
    }

    private fun loadData() {
        val tvShowsApiInterface: TvShowsApiInterface = TvShowsApiClient.retrofit
            .create(TvShowsApiInterface::class.java)

        val tvShowsOutlineCall: Call<TvShowsOutline> =
            tvShowsApiInterface.getAiringTodayTvShows(Utils.API_KEY)
        tvShowsOutlineCall.enqueue(object : Callback<TvShowsOutline> {
            override fun onResponse(
                call: Call<TvShowsOutline>,
                response: Response<TvShowsOutline>
            ) {
                if (response.isSuccessful && response.body()?.tvShowsOutlineList != null) {
                    // put data to list
                    tvShowsOutlineList = response.body()!!.tvShowsOutlineList

                    // set recyclerview
                    tvShowsOutlineAdapter = TvShowsOutlineAdapter(tvShowsOutlineList, this@AiringTodayFragment)
                    rvAiringToday.adapter = tvShowsOutlineAdapter
                } else {
                    Toast.makeText(activity, "Response failed.", Toast.LENGTH_SHORT).show()
                }

                // remove progress bar
                pbAiringToday.visibility = View.GONE
            }

            override fun onFailure(call: Call<TvShowsOutline>, t: Throwable) {
                Toast.makeText(activity, "Response failure.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun itemClicked(movieOutline: MovieOutline) {
        // do nothing
    }

    override fun itemClicked(tvShowOutline: TvShowsOutline) {
        // go to tv show detail
        val goToTvShowDetail = Intent(activity, TvShowDetailActivity::class.java)
        goToTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_ID, tvShowOutline.id)
        startActivity(goToTvShowDetail)
    }

    override fun itemClicked(genre: Genre) {
        // do nothing
    }

    override fun itemClicked(cast: Cast) {
        // do nothing
    }
}