package com.ardnn.mymovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ardnn.mymovies.R
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.Genre
import com.ardnn.mymovies.models.ImageSize
import com.ardnn.mymovies.models.Movie
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

    // movie
    private lateinit var movie: Movie
    private lateinit var moviesApiInterface: MoviesApiInterface

    // cast
    private lateinit var castList: List<Cast>

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
        setContentView(R.layout.activity_movie_detail)

        // initialization
        initialization()

        // load movies data
        loadMovieDetail()
        loadMoviesCast()

        // if button clicked
        btnBack.setOnClickListener { 
            finish()
        }
        btnFavorite.setOnClickListener {
            Toast.makeText(this, "${movie.title} has added to favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initialization() {
        // movie
        movieId = intent.getIntExtra(EXTRA_ID, 0)
        moviesApiInterface = MoviesApiClient.retrofit
            .create(MoviesApiInterface::class.java)


        // widgets
        tvTitle = findViewById(R.id.tv_title_movie_detail)
        tvReleaseDate = findViewById(R.id.tv_release_date_movie_detail)
        tvSynopsis = findViewById(R.id.tv_synopsis_movie_detail)
        tvVote = findViewById(R.id.tv_vote_movie_detail)

        ivWallpaper = findViewById(R.id.iv_wallpaper_movie_detail)
        ivPoster = findViewById(R.id.iv_poster_movie_detail)

        btnBack = findViewById(R.id.btn_back_movie_detail)
        btnFavorite = findViewById(R.id.btn_favorite_movie_detail)
    }

    private fun loadMovieDetail() {
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

    private fun loadMoviesCast() {
        val castCall: Call<Cast> = moviesApiInterface.getMoviesCast(movieId, Utils.API_KEY)
        castCall.enqueue(object : Callback<Cast> {
            override fun onResponse(call: Call<Cast>, response: Response<Cast>) {
                if (response.isSuccessful && response.body()?.castList != null) {
                    castList = response.body()!!.castList

                    // debug
                    for (cast in castList) {
                        Log.d("CAST", cast.name)
                    }
                } else {
                    Toast.makeText(this@MovieDetailActivity, "Response failed.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Cast>, t: Throwable) {
                Toast.makeText(this@MovieDetailActivity, "Response failure.", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setDataToWidgets() {
        // movie detail
        val title: String = movie.title
        val releaseDate: String = Utils.convertToDate(movie.releaseDate)
        val runtime: Int = movie.runtime
        val overview: String = movie.overview
        val rating: Float = movie.rating
        val wallpaperUrl: String = movie.getWallpaperUrl(ImageSize.W780)
        val posterUrl: String = movie.getPosterUrl(ImageSize.W342)

        val genreList: List<Genre> = movie.genreList
        for (genre in genreList) { // debug
            Log.d("GENRE", genre.name)
        }


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
