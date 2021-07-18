package com.ardnn.mymovies.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.ardnn.mymovies.adapters.MoviesSecondaryAdapter
import com.ardnn.mymovies.adapters.VideosAdapter
import com.ardnn.mymovies.api.callbacks.*
import com.ardnn.mymovies.api.repositories.MovieRepository
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.FilmDetailClickListener
import com.ardnn.mymovies.models.*
import com.google.android.material.button.MaterialButton

class MovieDetailActivity : AppCompatActivity(), View.OnClickListener, FilmDetailClickListener {
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

    // videos
    private lateinit var rvVideos: RecyclerView
    private lateinit var videosAdapter: VideosAdapter

    // similar movies
    private lateinit var rvSimilar: RecyclerView
    private lateinit var similarAdapter: MoviesSecondaryAdapter

    // recommendations
    private lateinit var rvRecommendations: RecyclerView
    private lateinit var recommendationsAdapter: MoviesSecondaryAdapter

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
    private lateinit var btnHome: MaterialButton
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
        btnHome.setOnClickListener(this)
        clWrapperSynopsis.setOnClickListener(this)
        ivImgsPosters.setOnClickListener(this)
        ivImgsBackdrops.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        // images detail intent
        val goToImagesDetail = Intent(this, ImagesDetailActivity::class.java)
        goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_ID, movieId)
        goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_ACTIVITY_KEY, ImagesDetailActivity.MOVIE)

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
                goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_IMAGES_KEY, ImagesDetailActivity.POSTERS)
                startActivity(goToImagesDetail)
            }
            R.id.iv_imgs_backdrops_movie_detail -> {
                goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_IMAGES_KEY, ImagesDetailActivity.BACKDROPS)
                startActivity(goToImagesDetail)
            }
            R.id.btn_home_movie_detail -> {
                // go to home and remove all activity
                val goToHome = Intent(this, MainActivity::class.java)
                goToHome.putExtra(MainActivity.EXTRA_NAV_KEY, 1)
                goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(goToHome)
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
            false)

        // casts
        rvCasts = findViewById(R.id.rv_casts_movie_detail)
        rvCasts.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

        // videos
        rvVideos = findViewById(R.id.rv_videos_movie_detail)
        rvVideos.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

        // similar movies
        rvSimilar = findViewById(R.id.rv_similar_movies)
        rvSimilar.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

        // recommendations
        rvRecommendations = findViewById(R.id.rv_recommendations_movie_detail)
        rvRecommendations.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

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
        btnHome = findViewById(R.id.btn_home_movie_detail)
        clWrapperSynopsis = findViewById(R.id.cl_wrapper_synopsis_movie_detail)
        pbDetail = findViewById(R.id.pb_movie_detail)
    }

    private fun loadMovieData() {
        loadMovieDetails()
        loadMovieCasts()
        loadMovieVideos()
        loadSimilarMovies()
        loadMovieRecommendations()
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
                // setup recyclerview videos
                videosAdapter = VideosAdapter(videoList, this@MovieDetailActivity)
                rvVideos.adapter = videosAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@MovieDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadSimilarMovies() {
        MovieRepository.getSimilarMovies(movieId, object : MovieOutlineCallback {
            override fun onSuccess(movieOutlineList: MutableList<MovieOutline>) {
                // setup recyclerview similar movies
                similarAdapter = MoviesSecondaryAdapter(movieOutlineList, this@MovieDetailActivity)
                rvSimilar.adapter = similarAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@MovieDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadMovieRecommendations() {
        MovieRepository.getMovieRecommendations(movieId, object : MovieOutlineCallback {
            override fun onSuccess(movieOutlineList: MutableList<MovieOutline>) {
                // setup recyclerview recommendations
                recommendationsAdapter = MoviesSecondaryAdapter(movieOutlineList, this@MovieDetailActivity)
                rvRecommendations.adapter = recommendationsAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@MovieDetailActivity, message, Toast.LENGTH_SHORT).show()
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
        Utils.setImageGlide(
            this,
            movie.getWallpaperUrl(ImageSize.W780),
            ivWallpaper, true)
        Utils.setImageGlide(
            this,
            movie.getPosterUrl(ImageSize.W342),
            ivPoster, true)
        Utils.setImageGlide(
            this,
            movie.getPosterUrl(ImageSize.W200),
            ivImgsPosters, true)
        Utils.setImageGlide(
            this,
            movie.getWallpaperUrl(ImageSize.W500),
            ivImgsBackdrops, true)

        // set rv genres
        genresAdapter = GenresAdapter(movie.genreList, this@MovieDetailActivity)
        rvGenres.adapter = genresAdapter

        // remove progress bar
        pbDetail.visibility = View.GONE
    }


    override fun onGenreClicked(genre: Genre) {
        Toast.makeText(this@MovieDetailActivity, genre.name, Toast.LENGTH_SHORT).show()
    }

    override fun onCastClicked(cast: Cast) {
        // go to person detail
        val goToPersonDetail = Intent(this, PersonDetailActivity::class.java)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_ID, cast.id)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_KNOWN_AS, cast.character)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_FILM, movie.title)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_WALLPAPER_URL, movie.getWallpaperUrl(ImageSize.W780))
        startActivity(goToPersonDetail)
    }

    override fun onVideoClicked(video: Video) {
        if (video.site.equals("YouTube", ignoreCase = true)) { // go to video player
            val goToVideoPlayer = Intent(this, VideoPlayerActivity::class.java)
            goToVideoPlayer.putExtra(VideoPlayerActivity.EXTRA_VIDEO_KEY, video.key)
            startActivity(goToVideoPlayer)
        } else {
            Toast.makeText(
                this,
                "This video can't be played due to an error occurred",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onSimilarClicked(movieOutline: MovieOutline) {
        // go to movie detail
        val goToMovieDetail = Intent(this, MovieDetailActivity::class.java)
        goToMovieDetail.putExtra(EXTRA_ID, movieOutline.id)
        startActivity(goToMovieDetail)
    }

    override fun onSimilarClicked(tvShowOutline: TvShowOutline) {
        // do nothing
    }
}
