package com.ardnn.mymovies.fragments.movies

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
import com.ardnn.mymovies.adapters.MoviesOutlineAdapter
import com.ardnn.mymovies.adapters.OnItemClick
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.MoviesOutline
import com.ardnn.mymovies.models.MoviesOutlineResponse
import com.ardnn.mymovies.networks.MoviesOutlineApiClient
import com.ardnn.mymovies.networks.MoviesOutlineApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularMoviesFragment : Fragment(), OnItemClick<MoviesOutline> {

    // recyclerview attr
    private lateinit var rvPopular: RecyclerView
    private lateinit var moviesOutlineAdapter: MoviesOutlineAdapter
    private lateinit var moviesOutlineList: List<MoviesOutline>

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

        val moviesOutlineApiInterface: MoviesOutlineApiInterface = MoviesOutlineApiClient.retrofit
            .create(MoviesOutlineApiInterface::class.java)

        val moviesOutlineResponseCall: Call<MoviesOutlineResponse> =
            moviesOutlineApiInterface.getPopularMovies(Utils.API_KEY)
        moviesOutlineResponseCall.enqueue(object : Callback<MoviesOutlineResponse> {
            override fun onResponse(
                call: Call<MoviesOutlineResponse>,
                response: Response<MoviesOutlineResponse>
            ) {
                if (response.isSuccessful && response.body()?.moviesOutlineList != null) {
                    // put MoviesNowPlaying's data to list
                    moviesOutlineList = response.body()!!.moviesOutlineList!!

                    // set recyclerview adapter
                    moviesOutlineAdapter = MoviesOutlineAdapter(moviesOutlineList, this@PopularMoviesFragment)
                    rvPopular.adapter = moviesOutlineAdapter
                } else {
                    Toast.makeText(activity, "Response failed.", Toast.LENGTH_SHORT).show()
                }

                // remove progress bar
                pbPopular.visibility = View.GONE
            }

            override fun onFailure(call: Call<MoviesOutlineResponse>, t: Throwable) {
                Toast.makeText(activity, "Response failed.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun itemClicked(data: MoviesOutline) {
        Toast.makeText(activity, "You clicked ${data.title}", Toast.LENGTH_SHORT).show()
    }

}