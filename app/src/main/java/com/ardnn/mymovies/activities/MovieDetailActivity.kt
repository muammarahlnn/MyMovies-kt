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
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.networks.MoviesApiClient
import com.ardnn.mymovies.networks.MoviesApiInterface
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    // classes
    private lateinit var movie: Movie

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
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // get movie id
        movieId = intent.getIntExtra(EXTRA_ID, 0)

        // load movies detail data
        loadMoviesDetailData()

        // initialize widgets
        initializeWidgets()

        // if button clicked
        btnBack.setOnClickListener { 
            finish()
        }
        btnFavorite.setOnClickListener {
            Toast.makeText(this, "${movie.title} has added to favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeWidgets() {
        tvTitle = findViewById(R.id.tv_title_detail)
        tvReleaseDate = findViewById(R.id.tv_release_date_detail)
        tvSynopsis = findViewById(R.id.tv_synopsis_detail)
        tvVote = findViewById(R.id.tv_vote_detail)

        ivWallpaper = findViewById(R.id.iv_wallpaper_detail)
        ivPoster = findViewById(R.id.iv_poster_detail)

        btnBack = findViewById(R.id.btn_back_detail)
        btnFavorite = findViewById(R.id.btn_favorite_detail)
    }

    private fun loadMoviesDetailData() {
        val moviesApiInterface: MoviesApiInterface = MoviesApiClient.retrofit
            .create(MoviesApiInterface::class.java)

        val movieCall: Call<Movie> = moviesApiInterface.getDetailMovies(movieId, Utils.API_KEY)
        movieCall.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful && response.body() != null) {
                    movie = response.body()!!
                    setDataToWidgets()
                } else {
                    Toast.makeText(this@MovieDetailActivity, "Response failed.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Toast.makeText(this@MovieDetailActivity, "Response failure.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setDataToWidgets() {
        // movie detail
        val title = movie.title
        val releaseDate = Utils.convertToDate(movie.releaseDate)
        val runtime = movie.runtime
        val overview = movie.overview
        val rating = movie.rating
        val wallpaperUrl = movie.getWallpaperUrl(ImageSize.W780)
        val posterUrl = movie.getPosterUrl(ImageSize.W342)

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
