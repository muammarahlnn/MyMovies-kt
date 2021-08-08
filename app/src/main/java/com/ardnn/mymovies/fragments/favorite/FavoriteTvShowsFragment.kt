package com.ardnn.mymovies.fragments.favorite

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.activities.MainActivity
import com.ardnn.mymovies.activities.TvShowDetailActivity
import com.ardnn.mymovies.adapters.FavoriteTvShowsAdapter
import com.ardnn.mymovies.database.viewmodels.FavoriteFilmViewModel
import com.ardnn.mymovies.database.entities.FavoriteTvShows
import com.ardnn.mymovies.listeners.SingleClickListener

class FavoriteTvShowsFragment : Fragment(), SingleClickListener<FavoriteTvShows> {

    // recyclerview favorite tv shows
    private lateinit var viewModel: FavoriteFilmViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteTvShowsAdapter

    // widgets
    private lateinit var tvEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_favorite_tv_shows, container, false)

        // initialization
        viewModel = ViewModelProvider(this).get(FavoriteFilmViewModel::class.java)
        tvEmpty = view.findViewById(R.id.tv_empty_favorite_tv_shows)
        recyclerView = view.findViewById(R.id.rv_favorite_tv_shows)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // get favorite tv shows from database
        viewModel.favoriteTvShowList.observe(viewLifecycleOwner, { favoriteTvShowList ->
            // set recyclerview adapter
            adapter = FavoriteTvShowsAdapter(favoriteTvShowList, this)
            recyclerView.adapter = adapter

            // check if favorite tv shows is empty then show the alert text and vice versa
            tvEmpty.visibility = if (favoriteTvShowList.isEmpty()) View.VISIBLE else View.GONE
        })

        return view
    }

    override fun onItemClicked(item: FavoriteTvShows) {
        // set nav key
        MainActivity.setNavKey(4)

        // go to tv show detail
        val goToTvShowDetail = Intent(activity, TvShowDetailActivity::class.java)
        goToTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_ID, item.id)
        startActivity(goToTvShowDetail)
    }
}