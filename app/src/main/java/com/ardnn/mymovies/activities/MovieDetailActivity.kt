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
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity(), View.OnClickListener, OnItemClick{
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    // movie
    private lateinit var movie: Movie
    private lateinit var moviesApiInterface: MoviesApiInterface

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
    private lateinit var tvReleaseDate: TextView
    private lateinit var tvRuntime: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvSynopsis: TextView
    private lateinit var tvMore: TextView
    private lateinit var ivWallpaper: ImageView
    private lateinit var ivPoster: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var btnFavorite: ImageView
    private lateinit var pbDetail: ProgressBar
    private lateinit var clWrapperSynopsis: ConstraintLayout

    // variables
    private var movieId: Int = 0
    private var isSynopsisExtended: Boolean = false
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // initialization
        initialization()

        // load movies data
        loadMovieDetail()
        loadMoviesCast()

        // if button clicked
        btnBack.setOnClickListener(this)
        btnFavorite.setOnClickListener(this)
        clWrapperSynopsis.setOnClickListener(this)
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
        }
    }

    private fun initialization() {
        // movie
        movieId = intent.getIntExtra(EXTRA_ID, 0)
        moviesApiInterface = MoviesApiClient.retrofit
            .create(MoviesApiInterface::class.java)

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
        btnBack = findViewById(R.id.btn_back_movie_detail)
        btnFavorite = findViewById(R.id.btn_favorite_movie_detail)
        clWrapperSynopsis = findViewById(R.id.cl_wrapper_synopsis_movie_detail)
        pbDetail = findViewById(R.id.pb_movie_detail)
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
                    // set rv casts
                    castList = response.body()!!.castList
                    castsAdapter = CastsAdapter(castList, this@MovieDetailActivity)
                    rvCasts.adapter = castsAdapter
                    for (cast in castList) { // debug
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

        // set rv genres
        genreList = movie.genreList
        genresAdapter = GenresAdapter(genreList, this@MovieDetailActivity)
        rvGenres.adapter = genresAdapter
        for (genre in genreList) { // debug
            Log.d("GENRE", genre.name)
        }

        // set to widgets
        tvTitle.text = title
        tvReleaseDate.text = releaseDate
        tvRuntime.text = "$runtime mins"
        tvRating.text = rating.toString()
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
        // do nothing
    }

    override fun itemClicked(tvShowOutline: TvShowOutline) {
        // do nothing
    }

    override fun itemClicked(genre: Genre) {
        Toast.makeText(this@MovieDetailActivity, genre.name, Toast.LENGTH_SHORT).show()
    }

    override fun itemClicked(cast: Cast) {
        Toast.makeText(this@MovieDetailActivity, cast.name, Toast.LENGTH_SHORT).show()
    }
}
