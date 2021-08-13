package com.ardnn.mymovies.fragments.movies

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ardnn.mymovies.R
import com.ardnn.mymovies.activities.MainActivity
import com.ardnn.mymovies.activities.MovieDetailActivity
import com.ardnn.mymovies.adapters.MoviesPrimaryAdapter
import com.ardnn.mymovies.api.callbacks.MovieOutlineCallback
import com.ardnn.mymovies.api.repositories.MovieRepository
import com.ardnn.mymovies.databinding.FragmentNowPlayingBinding
import com.ardnn.mymovies.listeners.SingleClickListener
import com.ardnn.mymovies.models.MovieOutline

class NowPlayingFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    SingleClickListener<MovieOutline> {

    // view binding
    private var _binding: FragmentNowPlayingBinding? = null
    private val binding get() = _binding!!

    // recyclerview attr
    private lateinit var adapter: MoviesPrimaryAdapter
    private val movieList = mutableListOf<MovieOutline>()

    // variables
    private var currentPage: Int = 1
    private var isFetching: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)

        // set swipe refresh layout
        binding.srlNowPlaying.setOnRefreshListener(this)

        // set recyclerview
        setRecyclerView()

        // load MoviesNowPlaying's data from TMDB API
        loadData(currentPage)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear() // prevent duplicated menu item
        inflater.inflate(R.menu.item_toolbar_main, menu)

        // if user is searching a movie
        val searchItem: MenuItem = menu.findItem(R.id.toolbar_item_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.setIsSearching(true)
                adapter.filter.filter(newText)
                return true
            }

        })
    }

    private fun loadData(page: Int) {
        MovieRepository.getNowPlayingMovies(page, object : MovieOutlineCallback {
            override fun onSuccess(movieOutlineList: MutableList<MovieOutline>) {
                if (page == 1) movieList.clear()
                movieList.addAll(movieOutlineList)
                adapter.updateList(movieList)

                // done fetching
                binding.pbNowPlaying.visibility = View.GONE
                isFetching = false
                binding.srlNowPlaying.isRefreshing = false
            }

            override fun onFailure(message: String) {}

        })
    }

    private fun setRecyclerView() {
        // set layout manager
        val layoutManager = GridLayoutManager(activity, 2)
        binding.rvNowPlaying.layoutManager = layoutManager

        // set adapter
        adapter = MoviesPrimaryAdapter(movieList, this)
        binding.rvNowPlaying.adapter = adapter

        // listener if recyclerview reached last item then fetch next page
        binding.rvNowPlaying.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItem: Int = layoutManager.itemCount
                val visibleItem: Int = layoutManager.childCount
                val firstVisibleItem: Int = layoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItem >= totalItem / 2) {
                    if (!isFetching && !adapter.getIsSearching()) {
                        isFetching = true
                        loadData(++currentPage)
                        isFetching = false
                    }
                }

            }
        })
    }

    override fun onRefresh() {
        // reset fetching
        currentPage = 1
        binding.pbNowPlaying.visibility = View.VISIBLE
        loadData(currentPage)
    }

    override fun onItemClicked(item: MovieOutline) {
        // set nav key
        MainActivity.setNavKey(1)

        // go to movie detail
        val goToMovieDetail = Intent(activity, MovieDetailActivity::class.java)
        goToMovieDetail.putExtra(MovieDetailActivity.EXTRA_ID, item.id)
        startActivity(goToMovieDetail)
    }
}
