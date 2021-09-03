package com.ardnn.mymovies.fragments.tvshows

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
import com.ardnn.mymovies.activities.TvShowDetailActivity
import com.ardnn.mymovies.adapters.TvShowsPrimaryAdapter
import com.ardnn.mymovies.api.callbacks.TvShowOutlineCallback
import com.ardnn.mymovies.api.repositories.TvShowRepository
import com.ardnn.mymovies.databinding.FragmentTvShowsBinding
import com.ardnn.mymovies.listeners.SingleClickListener
import com.ardnn.mymovies.models.TvShowOutline
import kotlinx.android.synthetic.main.activity_person_detail.*

class TvShowsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    SingleClickListener<TvShowOutline> {

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int) =
            TvShowsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
    }

    // view binding
    private var _binding: FragmentTvShowsBinding? = null
    private val binding get() = _binding!!

    // recyclerview
    private lateinit var adapter: TvShowsPrimaryAdapter
    private val tvShowList = mutableListOf<TvShowOutline>()

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
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTvShowsBinding.inflate(inflater, container, false)

        // get section page
        section = arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 0

        // set swipe refresh layout listener
        binding.srlTvShows.setOnRefreshListener(this)

        // set recycler view
        setRecyclerView()

        // load tv shows data depends on its section
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
        searchView.queryHint = resources.getString(R.string.search_hint_tv_shows)
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
        binding.pbTvShows.visibility = View.VISIBLE
        loadData(currentPage, section)
    }

    private fun setRecyclerView() {
        with(binding) {
            // set layout manager
            val layoutManager = GridLayoutManager(activity, 2)
            rvTvShows.layoutManager = layoutManager

            // set adapter
            adapter = TvShowsPrimaryAdapter(tvShowList, this@TvShowsFragment)
            rvTvShows.adapter = adapter

            // listener if rv reached last item then fetch the next page
            rvTvShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            0 -> {
                TvShowRepository.getAiringTodayTvShows(page, object : TvShowOutlineCallback {
                    override fun onSuccess(tvShowOutlineList: MutableList<TvShowOutline>) {
                        successFetchingData(tvShowOutlineList, page)
                    }

                    override fun onFailure(message: String) {}
                })
            }
            1 -> {
                TvShowRepository.getOnTheAirTvShows(page, object : TvShowOutlineCallback {
                    override fun onSuccess(tvShowOutlineList: MutableList<TvShowOutline>) {
                        successFetchingData(tvShowOutlineList, page)
                    }

                    override fun onFailure(message: String) {}

                })
            }
            2 -> {
                TvShowRepository.getPopularTvShows(page, object : TvShowOutlineCallback {
                    override fun onSuccess(tvShowOutlineList: MutableList<TvShowOutline>) {
                        successFetchingData(tvShowOutlineList, page)
                    }

                    override fun onFailure(message: String) {}
                })
            }
            3 -> {
                TvShowRepository.getTopRatedTvShows(page, object : TvShowOutlineCallback {
                    override fun onSuccess(tvShowOutlineList: MutableList<TvShowOutline>) {
                        successFetchingData(tvShowOutlineList, page)
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

    private fun successFetchingData(list: MutableList<TvShowOutline>, page: Int) {
        if (page == 1) tvShowList.clear()
        tvShowList.addAll(list)
        adapter.updateList(tvShowList)

        // done fetching
        with(binding) {
            pbTvShows.visibility = View.GONE
            srlTvShows.isRefreshing = false
        }
        isFetching = false
    }

    override fun onItemClicked(item: TvShowOutline) {
        // set nav key
        MainActivity.setNavKey(2)

        // go to tv show detail
        val goToTvShowDetail = Intent(activity, TvShowDetailActivity::class.java)
        goToTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_ID, item.id)
        startActivity(goToTvShowDetail)
    }

}