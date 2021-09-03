package com.ardnn.mymovies.fragments.movies

import android.app.SearchManager
import android.content.Context
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
import com.ardnn.mymovies.databinding.FragmentMoviesBinding
import com.ardnn.mymovies.listeners.SingleClickListener
import com.ardnn.mymovies.models.MovieOutline

class MoviesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    SingleClickListener<MovieOutline> {

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int) =
            MoviesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }

    }

    // view binding
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    // recyclerview
    private lateinit var adapter: MoviesPrimaryAdapter
    private val movieList = mutableListOf<MovieOutline>()

    // vars
    private var section = 0
    private var currentPage = 1
    private var isFetching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        // get section page
        section = arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 0

        // set swipe refresh layout listener
        binding.srlMovies.setOnRefreshListener(this)

        // set recycler view
        setRecyclerView()

        // load movies data depends on its section arg
        loadData(currentPage, section)

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
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.toolbar_item_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint_movies)
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

    override fun onRefresh() {
        // reset fetching
        currentPage = 1
        binding.pbMovies.visibility = View.VISIBLE
        loadData(currentPage, section)
    }

    private fun setRecyclerView() {
        with (binding) {
            // set layout manager
            val layoutManager = GridLayoutManager(activity, 2)
            rvMovies.layoutManager = layoutManager

            // set adapter
            adapter = MoviesPrimaryAdapter(movieList, this@MoviesFragment)
            rvMovies.adapter = adapter

            // listener if recyclerview reached last item then fetch next page
            rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val totalItem = layoutManager.itemCount
                    val visibleItem = layoutManager.childCount
                    val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                    if (firstVisibleItem + visibleItem >= totalItem / 2) {
                        if (!isFetching && !adapter.getIsSearching()) {
                            isFetching = true
                            loadData(++currentPage, section)
                            isFetching = false
                        }
                    }
                }
            })
        }

    }

    private fun loadData(page: Int, section: Int) {
        when (section) {
            0 -> { // now playing
                MovieRepository.getNowPlayingMovies(page, object : MovieOutlineCallback {
                    override fun onSuccess(movieOutlineList: MutableList<MovieOutline>) {
                        successFetchingData(movieOutlineList, page)
                    }

                    override fun onFailure(message: String) {}
                })
            }
            1 -> { // upcoming
                MovieRepository.getUpcomingMovies(page, object : MovieOutlineCallback {
                    override fun onSuccess(movieOutlineList: MutableList<MovieOutline>) {
                        successFetchingData(movieOutlineList, page)
                    }

                    override fun onFailure(message: String) {}
                })
            }
            2 -> { // popular
                MovieRepository.getPopularMovies(page, object : MovieOutlineCallback {
                    override fun onSuccess(movieOutlineList: MutableList<MovieOutline>) {
                        successFetchingData(movieOutlineList, page)

                    }

                    override fun onFailure(message: String) {}
                })
            }
            3 -> { // top rated
                MovieRepository.getTopRatedMovies(page, object : MovieOutlineCallback {
                    override fun onSuccess(movieOutlineList: MutableList<MovieOutline>) {
                        successFetchingData(movieOutlineList, page)
                    }

                    override fun onFailure(message: String) {}
                })
            }
            else -> {
                // will show alert to user if fetching is not successful
                // not implemented yet
            }
        }
    }

    private fun successFetchingData(list: MutableList<MovieOutline>, page: Int) {
        if (page == 1) movieList.clear()
        movieList.addAll(list)
        adapter.updateList(movieList)

        // done fetching
        with (binding) {
            pbMovies.visibility = View.GONE
            srlMovies.isRefreshing = false
        }
        isFetching = false
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