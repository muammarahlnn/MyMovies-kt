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
import com.ardnn.mymovies.models.TvShowOutline
import com.ardnn.mymovies.networks.TvShowsApiClient
import com.ardnn.mymovies.networks.TvShowsApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularTvShowsFragment : Fragment(), OnItemClick {

    // recyclerview attr
    private lateinit var rvPopular: RecyclerView
    private lateinit var tvShowsOutlineAdapter: TvShowsOutlineAdapter
    private lateinit var tvShowOutlineList: List<TvShowOutline>

    // widgets
    private lateinit var pbPopular: ProgressBar
    private lateinit var srlPopular: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_popular_tv_shows, container, false)

        // initialize widgets
        rvPopular = view.findViewById(R.id.rv_popular_tv_shows)
        pbPopular = view.findViewById(R.id.pb_popular_tv_shows)
        srlPopular = view.findViewById(R.id.srl_popular_tv_shows)
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
        val tvShowsApiInterface: TvShowsApiInterface = TvShowsApiClient.retrofit
            .create(TvShowsApiInterface::class.java)

        val tvShowOutlineCall: Call<TvShowOutline> =
            tvShowsApiInterface.getPopularTvShows(Utils.API_KEY)
        tvShowOutlineCall.enqueue(object : Callback<TvShowOutline> {
            override fun onResponse(
                call: Call<TvShowOutline>,
                response: Response<TvShowOutline>
            ) {
                if (response.isSuccessful && response.body()?.tvShowOutlineList != null) {
                    // put data to list
                    tvShowOutlineList = response.body()!!.tvShowOutlineList

                    // set recyclerview
                    tvShowsOutlineAdapter = TvShowsOutlineAdapter(tvShowOutlineList, this@PopularTvShowsFragment)
                    rvPopular.adapter = tvShowsOutlineAdapter
                } else {
                    Toast.makeText(activity, "Response failed.", Toast.LENGTH_SHORT).show()
                }

                // remove progress bar
                pbPopular.visibility = View.GONE
            }

            override fun onFailure(call: Call<TvShowOutline>, t: Throwable) {
                Toast.makeText(activity, "Response failure.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun itemClicked(movieOutline: MovieOutline) {
        // do nothing
    }

    override fun itemClicked(tvShowOutline: TvShowOutline) {
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