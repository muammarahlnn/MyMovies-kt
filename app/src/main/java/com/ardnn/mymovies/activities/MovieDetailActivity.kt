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
import com.ardnn.mymovies.api.callbacks.CastsCallback
import com.ardnn.mymovies.api.callbacks.ImagesCallback
import com.ardnn.mymovies.api.callbacks.VideosCallback
import com.ardnn.mymovies.api.callbacks.movies.*
import com.ardnn.mymovies.api.repositories.MovieRepository
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.*
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity(), View.OnClickListener, OnItemClick{
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    // movie
    private lateinit var movie: Movie
    private var movieId: Int = 0

    // genres
    private lateinit var rvGenres: RecyclerView
    private lateinit var genresAdapter: GenresAdapter

    // casts
    private lateinit var rvCasts: RecyclerView
    private lateinit var castsAdapter: CastsAdapter

    // widgets
    private lateinit var tvTitle: TextView
    private lateinit var tvReleaseDate: TextView
    private lateinit var tvRuntime: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvSynopsis: TextView
    private lateinit var tvMore: TextView
    private lateinit var ivWallpaper: ImageView
    private lateinit var ivPoster: ImageView
    private lateinit var ivImgsPosters: ImageView
    private lateinit var ivImgsBackdrops: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var btnFavorite: ImageView
    private lateinit var pbDetail: ProgressBar
    private lateinit var clWrapperSynopsis: ConstraintLayout

    // variables
    private var isSynopsisExtended: Boolean = false
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // initialization
        initialization()

        // load movie data
        loadMovieData()

        // if button clicked
        btnBack.setOnClickListener(this)
        btnFavorite.setOnClickListener(this)
        clWrapperSynopsis.setOnClickListener(this)
        ivImgsPosters.setOnClickListener(this)
        ivImgsBackdrops.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back_movie_detail -> {
                finish()
            }
            R.id.btn_favorite_movie_detail -> {
                isFavorite = !isFavorite
                if (isFavorite) { // like
                    btnFavorite.setImageResource(R.drawable.ic_favorite_true)
                    Toast.makeText(this, "You liked ${movie.title}", Toast.LENGTH_SHORT).show()
                } else { // dislike
                    btnFavorite.setImageResource(R.drawable.ic_favorite_false)
                    Toast.makeText(this, "You disliked ${movie.title}", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.cl_wrapper_synopsis_movie_detail -> {
                isSynopsisExtended = !isSynopsisExtended
                if (isSynopsisExtended) {
                    tvSynopsis.maxLines = Int.MAX_VALUE
                    tvMore.text = "less"
                } else {
                    tvSynopsis.maxLines = 2
                    tvMore.text = "more"
                }
            }
            R.id.iv_imgs_posters_movie_detail -> {
                val goToImagesDetail = Intent(this@MovieDetailActivity, ImagesDetailActivity::class.java)
                goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_ID, movieId)
                goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_KEY, ImagesDetailActivity.POSTERS)
                startActivity(goToImagesDetail)
            }
            R.id.iv_imgs_backdrops_movie_detail -> {
                val goToImagesDetail = Intent(this@MovieDetailActivity, ImagesDetailActivity::class.java)
                goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_ID, movieId)
                goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_KEY, ImagesDetailActivity.BACKDROPS)
                startActivity(goToImagesDetail)
            }
        }
    }

    private fun initialization() {
        // movie
        movieId = intent.getIntExtra(EXTRA_ID, 0)

        // genres
        rvGenres = findViewById(R.id.rv_genre_movie_detail)
        rvGenres.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // casts
        rvCasts = findViewById(R.id.rv_casts_movie_detail)
        rvCasts.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // widgets
        tvTitle = findViewById(R.id.tv_title_movie_detail)
        tvReleaseDate = findViewById(R.id.tv_release_date_movie_detail)
        tvRuntime = findViewById(R.id.tv_runtime_movie_detail)
        tvRating = findViewById(R.id.tv_rating_movie_detail)
        tvSynopsis = findViewById(R.id.tv_synopsis_movie_detail)
        tvMore = findViewById(R.id.tv_more_movie_detail)
        ivWallpaper = findViewById(R.id.iv_wallpaper_movie_detail)
        ivPoster = findViewById(R.id.iv_poster_movie_detail)
        ivImgsPosters = findViewById(R.id.iv_imgs_posters_movie_detail)
        ivImgsBackdrops = findViewById(R.id.iv_imgs_backdrops_movie_detail)
        btnBack = findViewById(R.id.btn_back_movie_detail)
        btnFavorite = findViewById(R.id.btn_favorite_movie_detail)
        clWrapperSynopsis = findViewById(R.id.cl_wrapper_synopsis_movie_detail)
        pbDetail = findViewById(R.id.pb_movie_detail)
    }

    private fun loadMovieData() {
        loadMovieDetails()
        loadMovieCasts()
        loadMovieVideos()
        loadSimilarMovie()
    }

    private fun loadMovieDetails() {
        MovieRepository.getMovieDetails(movieId, object : MovieDetailsCallback {
            override fun onSuccess(movie: Movie) {
                // get details and set it to widgets
                this@MovieDetailActivity.movie = movie
                setDataToWidgets()
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@MovieDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadMovieCasts() {
        MovieRepository.getMovieCasts(movieId, object : CastsCallback {
            override fun onSuccess(castList: List<Cast>) {
                // setup recyclerview casts
                castsAdapter = CastsAdapter(castList, this@MovieDetailActivity)
                rvCasts.adapter = castsAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@MovieDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun loadMovieVideos() {
        MovieRepository.getMovieVideos(movieId, object : VideosCallback {
            override fun onSuccess(videoList: List<Video>) {
                for (video in videoList) {
                    Log.d("MOVIE VIDEO", video.name ?: "null")
                }
            }

            override fun onFailure(message: String) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun loadSimilarMovie() {
        MovieRepository.getSimilarMovies(movieId, object : MovieOutlineCallback {
            override fun onSuccess(movieOutlineList: MutableList<MovieOutline>) {
                for (movie in movieOutlineList) {
                    Log.d("SIMILAR", movie.title ?: "null")
                }
            }

            override fun onFailure(message: String) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataToWidgets() {
        // set to widgets
        tvTitle.text = movie.title ?: "-"
        tvReleaseDate.text =
            if (movie.releaseDate != null)
                Utils.convertToDate(movie.releaseDate)
            else
                "-"
        tvRuntime.text =
            if (movie.runtime != null)
                "${movie.runtime} mins"
            else
                "-"
        tvRating.text = (movie.rating ?: "-").toString()
        tvSynopsis.text = movie.overview ?: "-"
        Glide.with(this)
            .load(movie.getWallpaperUrl(ImageSize.W780))
            .into(ivWallpaper)
        Glide.with(this)
            .load(movie.getPosterUrl(ImageSize.W342))
            .into(ivPoster)
        Glide.with(this)
            .load(movie.getPosterUrl(ImageSize.W200))
            .into(ivImgsPosters)
        Glide.with(this)
            .load(movie.getWallpaperUrl(ImageSize.W500))
            .into(ivImgsBackdrops)


        // set rv genres
        genresAdapter = GenresAdapter(movie.genreList, this@MovieDetailActivity)
        rvGenres.adapter = genresAdapter

        // remove progress bar
        pbDetail.visibility = View.GONE
    }

    override fun itemClicked(genre: Genre) {
        Toast.makeText(this@MovieDetailActivity, genre.name, Toast.LENGTH_SHORT).show()
    }

    override fun itemClicked(cast: Cast) {
        // go to person detail
        val goToPersonDetail = Intent(this, PersonDetailActivity::class.java)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_ID, cast.id)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_KNOWN_AS, cast.character)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_FILM, movie.title)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_WALLPAPER_URL, movie.getWallpaperUrl(ImageSize.W780))
        startActivity(goToPersonDetail)
    }

    // do nothing
    override fun itemClicked(movieOutline: MovieOutline) {}
    override fun itemClicked(tvShowOutline: TvShowOutline) {}
}
