package com.ardnn.mymovies.fragments.recent

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.mymovies.R
import com.ardnn.mymovies.activities.MovieDetailActivity
import com.ardnn.mymovies.activities.TvShowDetailActivity
import com.ardnn.mymovies.adapters.RecentsAdapter
import com.ardnn.mymovies.database.entities.RecentFilms
import com.ardnn.mymovies.database.viewmodels.RecentFilmViewModel
import com.ardnn.mymovies.databinding.FragmentRecentBinding
import com.ardnn.mymovies.listeners.RecentsClickListener

class RecentFragment : Fragment(), RecentsClickListener {

    private var _binding: FragmentRecentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RecentFilmViewModel
    private lateinit var adapter: RecentsAdapter
    private var isListEmpty: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecentBinding.inflate(inflater, container, false)

        // initialization
        viewModel = ViewModelProvider(this).get(RecentFilmViewModel::class.java)
        binding.rvRecents.layoutManager = LinearLayoutManager(activity)

        // get recent list from database
        viewModel.recentFilmList.observe(viewLifecycleOwner, { recentFilmList ->
            // set recyclerview adapter
            adapter = RecentsAdapter(recentFilmList, this)
            binding.rvRecents.adapter = adapter

            // check if favorite movies is not empty then show the alert text and vice versa
            binding.tvEmptyRecents.visibility =
                if (recentFilmList.isEmpty()) View.VISIBLE else View.GONE
        })

        // check if recents is empty or not
        viewModel.recentFilmList.observe(viewLifecycleOwner, {recentFilmList ->
            if (recentFilmList.isEmpty()) {
                isListEmpty = true
            }
        })


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.item_toolbar_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.toolbar_item_delete) {
            deleteAllRecents()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllRecents() {
        // check if recents is already empty
        if (isListEmpty) {
            Toast.makeText(
                activity,
                "Your recent films is already empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // create an alert
        val alert = AlertDialog.Builder(requireContext())
        alert.setMessage("Are you sure want to clear your recents?")
        alert.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAllRecentFilms()

            Toast.makeText(
                activity,
                "Your recent films successfully cleared",
                Toast.LENGTH_SHORT
            ).show()
        }
        alert.setNegativeButton("No", null)
        alert.create().show()
    }

    private fun deleteRecent(recentFilm: RecentFilms) {
        viewModel.deleteRecentFilm(recentFilm)
        Toast.makeText(
            activity,
            "${recentFilm.title} has deleted from recents",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun itemClicked(recentFilm: RecentFilms) {
        when {
            recentFilm.movieId != -1 -> {
                // go to movie detail
                val goToMovieDetail = Intent(activity, MovieDetailActivity::class.java)
                goToMovieDetail.putExtra(MovieDetailActivity.EXTRA_ID, recentFilm.movieId)
                startActivity(goToMovieDetail)
            }
            recentFilm.tvShowId != -1 -> {
                val goToTvShowDetail = Intent(activity, TvShowDetailActivity::class.java)
                goToTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_ID, recentFilm.tvShowId)
                startActivity(goToTvShowDetail)
            }
            else -> {
                Toast.makeText(
                    activity,
                    "Can not do the operation due an error occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun btnDeleteClicked(recentFilm: RecentFilms) {
        deleteRecent(recentFilm)
    }

}