package com.ardnn.mymovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ardnn.mymovies.R
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.ImageSize
import com.ardnn.mymovies.models.Movie
import com.ardnn.mymovies.models.TvShow
import com.ardnn.mymovies.networks.MoviesApiClient
import com.ardnn.mymovies.networks.MoviesApiInterface
import com.ardnn.mymovies.networks.TvShowsApiClient
import com.ardnn.mymovies.networks.TvShowsApiInterface
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    // classes
    private lateinit var tvShow: TvShow

    // widgets
    private lateinit var tvTitle: TextView
    private lateinit var tvReleaseDate: TextView
    private lateinit var tvSynopsis: TextView
    private lateinit var tvVote: TextView
    private lateinit var ivWallpaper: ImageView
    private lateinit var ivPoster: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var btnFavorite: ImageView

    // attributes
    private var tvShowId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        // get tvShow id
        tvShowId = intent.getIntExtra(EXTRA_ID, 0)

        // load movies detail data
        loadTvShowDetailData()

        // initialize widgets
        initializeWidgets()

        // if button clicked
        btnBack.setOnClickListener {
            finish()
        }
        btnFavorite.setOnClickListener {
            Toast.makeText(this, "${tvShow.title} has added to favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeWidgets() {
        tvTitle = findViewById(R.id.tv_title_tv_show_detail)
        tvReleaseDate = findViewById(R.id.tv_release_date_tv_show_detail)
        tvSynopsis = findViewById(R.id.tv_synopsis_tv_show_detail)
        tvVote = findViewById(R.id.tv_vote_tv_show_detail)

        ivWallpaper = findViewById(R.id.iv_wallpaper_tv_show_detail)
        ivPoster = findViewById(R.id.iv_poster_tv_show_detail)

        btnBack = findViewById(R.id.btn_back_tv_show_detail)
        btnFavorite = findViewById(R.id.btn_favorite_tv_show_detail)
    }

    private fun loadTvShowDetailData() {
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

    private fun setDataToWidgets() {
        // tvShow detail
        val title = tvShow.title
        val releaseDate = Utils.convertToDate(tvShow.firstAirDate)
        val runtime = tvShow.runtimes[0]
        val overview = tvShow.overview
        val rating = tvShow.rating
        val wallpaperUrl = tvShow.getWallpaperUrl(ImageSize.W780)
        val posterUrl = tvShow.getPosterUrl(ImageSize.W342)

        // set to widgets
        tvTitle.text = title
        tvReleaseDate.text = releaseDate
        tvSynopsis.text = overview
        tvVote.text = rating.toString()
        Glide.with(this)
            .load(wallpaperUrl)
            .into(ivWallpaper)
        Glide.with(this)
            .load(posterUrl)
            .into(ivPoster)

    }
}
