package com.ardnn.mymovies.fragments.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.mymovies.activities.MainActivity
import com.ardnn.mymovies.activities.TvShowDetailActivity
import com.ardnn.mymovies.adapters.FavoriteTvShowsAdapter
import com.ardnn.mymovies.database.entities.FavoriteTvShows
import com.ardnn.mymovies.database.viewmodels.FavoriteFilmViewModel
import com.ardnn.mymovies.databinding.FragmentFavoriteTvShowsBinding
import com.ardnn.mymovies.listeners.SingleClickListener

class FavoriteTvShowsFragment : Fragment(), SingleClickListener<FavoriteTvShows> {

    private var _binding: FragmentFavoriteTvShowsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteFilmViewModel
    private lateinit var adapter: FavoriteTvShowsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteTvShowsBinding.inflate(inflater, container, false)

        // initialization
        viewModel = ViewModelProvider(this).get(FavoriteFilmViewModel::class.java)
        binding.rvFavoriteTvShows.layoutManager = LinearLayoutManager(activity)

        // get favorite tv shows from database
        viewModel.favoriteTvShowList.observe(viewLifecycleOwner, { favoriteTvShowList ->
            // set recyclerview adapter
            adapter = FavoriteTvShowsAdapter(favoriteTvShowList, this)
            binding.rvFavoriteTvShows.adapter = adapter

            // check if favorite tv shows is empty then show the alert text and vice versa
            binding.tvEmptyFavoriteTvShows.visibility =
                if (favoriteTvShowList.isEmpty()) View.VISIBLE else View.GONE
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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