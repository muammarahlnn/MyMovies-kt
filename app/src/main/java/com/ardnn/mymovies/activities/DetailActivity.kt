package com.ardnn.mymovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ardnn.mymovies.R
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.MoviesOutline
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    // classes
    private lateinit var movie: MoviesOutline

    // widgets
    private lateinit var tvTitle: TextView
    private lateinit var tvReleaseDate: TextView
    private lateinit var tvSynopsis: TextView
    private lateinit var tvVote: TextView
    private lateinit var ivWallpaper: ImageView
    private lateinit var ivPoster: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var btnFavorite: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // initialize widgets
        initializeWidgets()

        // set data to widgets
//        setDataToWidgets()
        
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

//    private fun setDataToWidgets() {
//        // get MoviesNowPlaying's parcelable from previous intent
//        movie = intent.getParcelableExtra<MoviesOutline>(EXTRA_MOVIE) as MoviesOutline
//
//        // list -> [title, releaseDate, synopsis, vote, wallpaperUrl, posterUrl]
//        val movieData = listOf(
//            movie.title,
//            Utils.convertToDate(movie.releaseDate),
//            movie.synopsis,
//            movie.vote.toString(),
//            movie.wallpaperUrl,
//            movie.posterUrl
//        )
//
//        // set to widgets
//        tvTitle.text = movieData[0]
//        tvReleaseDate.text = movieData[1]
//        tvSynopsis.text = movieData[2]
//        tvVote.text = movieData[3]
//        Glide.with(this)
//            .load("${Utils.IMG_URL_500}${movieData[4]}")
//            .into(ivWallpaper)
//        Glide.with(this)
//            .load("${Utils.IMG_URL_300}${movieData[5]}")
//            .into(ivPoster)
//
//    }
}
