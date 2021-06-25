package com.ardnn.mymovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.CastsAdapter
import com.ardnn.mymovies.adapters.GenresAdapter
import com.ardnn.mymovies.adapters.OnItemClick
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.*
import com.ardnn.mymovies.networks.MoviesApiClient
import com.ardnn.mymovies.networks.MoviesApiInterface
import com.ardnn.mymovies.networks.TvShowsApiClient
import com.ardnn.mymovies.networks.TvShowsApiInterface
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowDetailActivity : AppCompatActivity(), OnItemClick, View.OnClickListener {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    // tv show
    private lateinit var tvShow: TvShow
    private lateinit var tvShowApiInterface: TvShowsApiInterface

    // genres
    private lateinit var rvGenres: RecyclerView
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var genreList: List<Genre>

    // casts
    private lateinit var rvCasts: RecyclerView
    private lateinit var castsAdapter: CastsAdapter
    private lateinit var castList: List<Cast>

    // widgets
    private lateinit var tvTitle: TextView
    private lateinit var tvEpisodes: TextView
    private lateinit var tvSeasons: TextView
    private lateinit var tvRuntime: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvFirstAiring: TextView
    private lateinit var tvLastAiring: TextView
    private lateinit var tvSynopsis: TextView
    private lateinit var tvMore: TextView
    private lateinit var ivWallpaper: ImageView
    private lateinit var ivPoster: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var btnFavorite: ImageView
    private lateinit var clWrapperSynopsis: ConstraintLayout
    private lateinit var pbDetail: ProgressBar

    // variables
    private var tvShowId: Int = 0
    private var isSynopsisExtended: Boolean = false
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        // initialization
        initialization()

        // load tv show data
        loadTvShowDetail()
        loadTvShowCast()

        // if button clicked
        btnBack.setOnClickListener(this)
        btnFavorite.setOnClickListener(this)
        clWrapperSynopsis.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back_tv_show_detail -> {
                finish()
            }
            R.id.btn_favorite_tv_show_detail -> {
                isFavorite = !isFavorite
                if (isFavorite) { // like
                    btnFavorite.setImageResource(R.drawable.ic_favorite_true)
                    Toast.makeText(this, "You liked ${tvShow.title}", Toast.LENGTH_SHORT).show()
                } else { // dislike
                    btnFavorite.setImageResource(R.drawable.ic_favorite_false)
                    Toast.makeText(this, "You disliked ${tvShow.title}", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.cl_wrapper_synopsis_tv_show_detail -> {
                isSynopsisExtended = !isSynopsisExtended
                if (isSynopsisExtended) {
                    tvSynopsis.maxLines = Int.MAX_VALUE
                    tvMore.text = "less"
                } else {
                    tvSynopsis.maxLines = 2
                    tvMore.text = "more"
                }
            }
        }
    }

    private fun initialization() {
        // tv show
        tvShowId = intent.getIntExtra(EXTRA_ID, 0)
        tvShowApiInterface = TvShowsApiClient.retrofit
            .create(TvShowsApiInterface::class.java)

        // genres
        rvGenres = findViewById(R.id.rv_genre_tv_show_detail)
        rvGenres.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // casts
        rvCasts = findViewById(R.id.rv_casts_tv_show_detail)
        rvCasts.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // widgets
        tvTitle = findViewById(R.id.tv_title_tv_show_detail)
        tvEpisodes = findViewById(R.id.tv_episodes_tv_show_detail)
        tvSeasons = findViewById(R.id.tv_seasons_tv_show_detail)
        tvRuntime = findViewById(R.id.tv_runtime_tv_show_detail)
        tvRating = findViewById(R.id.tv_rating_tv_show_detail)
        tvFirstAiring = findViewById(R.id.tv_first_airing)
        tvLastAiring = findViewById(R.id.tv_last_airing)
        tvSynopsis = findViewById(R.id.tv_synopsis_tv_show_detail)
        tvMore = findViewById(R.id.tv_more_tv_show_detail)
        ivWallpaper = findViewById(R.id.iv_wallpaper_tv_show_detail)
        ivPoster = findViewById(R.id.iv_poster_tv_show_detail)
        btnBack = findViewById(R.id.btn_back_tv_show_detail)
        btnFavorite = findViewById(R.id.btn_favorite_tv_show_detail)
        clWrapperSynopsis = findViewById(R.id.cl_wrapper_synopsis_tv_show_detail)
        pbDetail = findViewById(R.id.pb_tv_show_detail)
    }

    private fun loadTvShowDetail() {
        val tvShowsApiInterface: TvShowsApiInterface = TvShowsApiClient.retrofit
            .create(TvShowsApiInterface::class.java)

        val tvShowCall: Call<TvShow> = tvShowsApiInterface.getDetailTvShows(tvShowId, Utils.API_KEY)
        tvShowCall.enqueue(object : Callback<TvShow>{
            override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
                if (response.isSuccessful && response.body() != null) {
                    tvShow = response.body()!!
                    setDataToWidgets()
                } else {
                    Toast.makeText(this@TvShowDetailActivity, "Response failed.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TvShow>, t: Throwable) {
                Toast.makeText(this@TvShowDetailActivity, "Response failure.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadTvShowCast() {
        val castCall: Call<Cast> = tvShowApiInterface.getTvShowsCast(tvShowId, Utils.API_KEY)
        castCall.enqueue(object : Callback<Cast> {
            override fun onResponse(call: Call<Cast>, response: Response<Cast>) {
                if (response.isSuccessful && response.body()?.castList != null) {
                    // set rv casts
                    castList = response.body()!!.castList
                    castsAdapter = CastsAdapter(castList, this@TvShowDetailActivity)
                    rvCasts.adapter = castsAdapter
                    for (cast in castList) { // debug
                        Log.d("CAST", cast.name)
                    }
                } else {
                    Toast.makeText(this@TvShowDetailActivity, "Response failed.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Cast>, t: Throwable) {
                Toast.makeText(this@TvShowDetailActivity, "Response failure.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setDataToWidgets() {
        // tvShow detail
        val title: String = tvShow.title
        val episodes: Int = tvShow.numberOfEpisodes
        val seasons: Int = tvShow.numberOfSeasons
        val runtime: Int = tvShow.runtimes[0]
        val rating: Float = tvShow.rating ?: 0.0F
        val firstAiring: String = Utils.convertToDate(tvShow.firstAirDate)
        val lastAiring: String =
            if (tvShow.lastAirDate != null) Utils.convertToDate(tvShow.lastAirDate!!)
            else "Not yet known"
        val overview: String = tvShow.overview
        val wallpaperUrl: String = tvShow.getWallpaperUrl(ImageSize.W780)
        val posterUrl: String = tvShow.getPosterUrl(ImageSize.W342)

        // set rv genres
        genreList = tvShow.genreList
        genresAdapter = GenresAdapter(genreList, this)
        rvGenres.adapter = genresAdapter
        for (genre in genreList) { // debug
            Log.d("GENRE", genre.name)
        }

        // set to widgets
        tvTitle.text = title
        tvEpisodes.text = episodes.toString()
        tvSeasons.text = seasons.toString()
        tvRuntime.text = "$runtime mins"
        tvRating.text = rating.toString()
        tvFirstAiring.text = firstAiring
        tvLastAiring.text = lastAiring
        tvSynopsis.text = overview
        Glide.with(this)
            .load(wallpaperUrl)
            .into(ivWallpaper)
        Glide.with(this)
            .load(posterUrl)
            .into(ivPoster)

        // remove progress bar
        pbDetail.visibility = View.GONE

    }

    override fun itemClicked(movieOutline: MovieOutline) {
        TODO("Not yet implemented")
    }

    override fun itemClicked(tvShowOutline: TvShowOutline) {
        TODO("Not yet implemented")
    }

    override fun itemClicked(genre: Genre) {
        Toast.makeText(this, genre.name, Toast.LENGTH_SHORT).show()
    }

    override fun itemClicked(cast: Cast) {
        Toast.makeText(this, cast.name, Toast.LENGTH_SHORT).show()
    }

}
