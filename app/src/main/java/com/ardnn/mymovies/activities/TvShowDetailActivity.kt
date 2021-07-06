package com.ardnn.mymovies.activities

import android.content.Intent
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
import com.ardnn.mymovies.api.callbacks.tvshows.TvShowCastsCallback
import com.ardnn.mymovies.api.callbacks.tvshows.TvShowDetailsCallback
import com.ardnn.mymovies.api.callbacks.tvshows.TvShowVideosCallback
import com.ardnn.mymovies.api.repositories.TvShowRepository
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.*
import com.ardnn.mymovies.api.services.TvShowApiServices
import com.bumptech.glide.Glide

class TvShowDetailActivity : AppCompatActivity(), OnItemClick, View.OnClickListener {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    // tv show
    private lateinit var tvShow: TvShow
    private var tvShowId: Int = 0


    // genres
    private lateinit var rvGenres: RecyclerView
    private lateinit var genresAdapter: GenresAdapter

    // casts
    private lateinit var rvCasts: RecyclerView
    private lateinit var castsAdapter: CastsAdapter

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
    private var isSynopsisExtended: Boolean = false
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        // initialization
        initialization()

        // load tv show data
        loadTvShowDetails()
        loadTvShowCasts()
        loadTvShowVideos()

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

    private fun loadTvShowDetails() {
        TvShowRepository.getTvShowDetails(tvShowId, object : TvShowDetailsCallback {
            override fun onSuccess(tvShow: TvShow) {
                // get details and set it to widgets
                this@TvShowDetailActivity.tvShow = tvShow
                setDataToWidgets()
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@TvShowDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadTvShowCasts() {
        TvShowRepository.getTvShowCasts(tvShowId, object : TvShowCastsCallback {
            override fun onSuccess(castList: List<Cast>) {
                // set recyclerview casts
                castsAdapter = CastsAdapter(castList, this@TvShowDetailActivity)
                rvCasts.adapter = castsAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@TvShowDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadTvShowVideos() {
        TvShowRepository.getTvShowVideos(tvShowId, object : TvShowVideosCallback {
            override fun onSuccess(videoList: MutableList<Video>) {
                for (video in videoList) {
                    Log.d("TV SHOW VIDEO", video.name ?: "null")
                }
            }

            override fun onFailure(message: String) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataToWidgets() {
        // set to widgets
        tvTitle.text = tvShow.title ?: "-"
        tvEpisodes.text = (tvShow.numberOfEpisodes ?: "-").toString()
        tvSeasons.text = (tvShow.numberOfSeasons ?: "-").toString()
        tvRuntime.text =
            if (tvShow.runtimes != null && tvShow.runtimes?.size != 0)
                "${tvShow.runtimes?.get(0)} mins"
            else
                "-"
        tvRating.text = (tvShow.rating ?: "-").toString()
        tvFirstAiring.text =
            if (tvShow.firstAirDate != null)
                Utils.convertToDate(tvShow.firstAirDate)
            else
                "-"
        tvLastAiring.text =
            if (tvShow.lastAirDate != null)
                Utils.convertToDate(tvShow.lastAirDate)
            else
                "-"
        tvSynopsis.text = tvShow.overview ?: "-"
        Glide.with(this)
            .load(tvShow.getWallpaperUrl(ImageSize.W780))
            .into(ivWallpaper)
        Glide.with(this)
            .load(tvShow.getPosterUrl(ImageSize.W342))
            .into(ivPoster)

        // set rv genres
        genresAdapter = GenresAdapter(tvShow.genreList, this)
        rvGenres.adapter = genresAdapter

        // remove progress bar
        pbDetail.visibility = View.GONE

    }

    override fun itemClicked(genre: Genre) {
        Toast.makeText(this, genre.name, Toast.LENGTH_SHORT).show()
    }

    override fun itemClicked(cast: Cast) {
        // go to person detail
        val goToPersonDetail = Intent(this, PersonDetailActivity::class.java)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_ID, cast.id)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_KNOWN_AS, cast.character)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_FILM, tvShow.title)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_WALLPAPER_URL, tvShow.getWallpaperUrl(ImageSize.W780))
        startActivity(goToPersonDetail)
    }


    // do nothing
    override fun itemClicked(movieOutline: MovieOutline) {}
    override fun itemClicked(tvShowOutline: TvShowOutline) {}

}
