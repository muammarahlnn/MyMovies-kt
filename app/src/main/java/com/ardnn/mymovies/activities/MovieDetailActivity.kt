package com.ardnn.mymovies.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.CastsAdapter
import com.ardnn.mymovies.adapters.GenresAdapter
import com.ardnn.mymovies.adapters.MoviesSecondaryAdapter
import com.ardnn.mymovies.adapters.VideosAdapter
import com.ardnn.mymovies.api.callbacks.*
import com.ardnn.mymovies.api.repositories.MovieRepository
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.database.entities.RecentFilms
import com.ardnn.mymovies.database.viewmodels.FavoriteFilmViewModel
import com.ardnn.mymovies.database.viewmodels.RecentFilmViewModel
import com.ardnn.mymovies.databinding.ActivityMovieDetailBinding
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.FilmDetailClickListener
import com.ardnn.mymovies.models.*
import kotlinx.coroutines.runBlocking

class MovieDetailActivity : AppCompatActivity(), View.OnClickListener, FilmDetailClickListener {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    // view model
    private lateinit var favoriteViewModel: FavoriteFilmViewModel
    private lateinit var recentViewModel: RecentFilmViewModel

    // view binding
    private lateinit var binding: ActivityMovieDetailBinding

    // movie
    private lateinit var movie: Movie
    private var movieId: Int = 0

    // adapters
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var castsAdapter: CastsAdapter
    private lateinit var videosAdapter: VideosAdapter
    private lateinit var similarAdapter: MoviesSecondaryAdapter
    private lateinit var recommendationsAdapter: MoviesSecondaryAdapter


    // variables
    private var isSynopsisExtended: Boolean = false
    private var isFavorite: Boolean = false
    private var isRecent: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialization
        initialization()

        // load movie data
        loadMovieData()

        // if button clicked
        with (binding) {
            btnBackMovieDetail.setOnClickListener(this@MovieDetailActivity)
            btnFavoriteMovieDetail.setOnClickListener(this@MovieDetailActivity)
            btnHomeMovieDetail.setOnClickListener(this@MovieDetailActivity)
            clWrapperSynopsisMovieDetail.setOnClickListener(this@MovieDetailActivity)
            ivImgsPostersMovieDetail.setOnClickListener(this@MovieDetailActivity)
            ivImgsBackdropsMovieDetail.setOnClickListener(this@MovieDetailActivity)
        }
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
                    addMovieToDatabase()
                } else { // dislike
                    deleteMovieFromDatabase()
                }
            }
            R.id.cl_wrapper_synopsis_movie_detail -> {
                isSynopsisExtended = !isSynopsisExtended
                if (isSynopsisExtended) {
                    binding.tvSynopsisMovieDetail.maxLines = Int.MAX_VALUE
                    binding.tvMoreMovieDetail.text = "less"
                } else {
                    binding.tvSynopsisMovieDetail.maxLines = 2
                    binding.tvMoreMovieDetail.text = "more"
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
                goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(goToHome)
            }
        }
    }

    private fun initialization() {
        // view model
        favoriteViewModel = ViewModelProvider(this).get(FavoriteFilmViewModel::class.java)
        recentViewModel = ViewModelProvider(this).get(RecentFilmViewModel::class.java)

        // movie
        movieId = intent.getIntExtra(EXTRA_ID, 0)

        with (binding) {
            // genres
            rvGenreMovieDetail.layoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false)

            // casts
            rvCastsMovieDetail.layoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false)

            // videos
            rvVideosMovieDetail.layoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false)

            // similar movies
            rvSimilarMovies.layoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false)

            // recommendations
            rvRecommendationsMovieDetail.layoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false)

            // set btn favorite
            runBlocking {
                isFavorite = favoriteViewModel.isMovieExists(movieId)
            }
            btnFavoriteMovieDetail.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_true else R.drawable.ic_favorite_false)

        }

    }


    private fun addMovieToDatabase() {
        // create favorite movie object
        val id: Int = movieId
        val title: String = movie.title ?: "-"
        val releaseDate: String = movie.releaseDate ?: "-"
        val posterUrl: String = movie.getPosterUrl(ImageSize.W200)
        val rating: Float = movie.rating ?: -1F
        val favoriteMovie = FavoriteMovies(id, title, releaseDate, posterUrl, rating)
        
        // insert to database
        favoriteViewModel.addMovie(favoriteMovie)

        // set icon favorite to true and notify the user
        binding.btnFavoriteMovieDetail.setImageResource(R.drawable.ic_favorite_true)
        Toast.makeText(this, "$title has added to favorites", Toast.LENGTH_SHORT).show()
    }

    private fun deleteMovieFromDatabase() {
        // get movie from database
        lateinit var favoriteMovie: FavoriteMovies
        runBlocking {
            favoriteMovie = favoriteViewModel.getMovie(movieId)
        }

        // delete it from database
        favoriteViewModel.deleteMovie(favoriteMovie)

        // set icon favorite to false and notify the user
        binding.btnFavoriteMovieDetail.setImageResource(R.drawable.ic_favorite_false)
        Toast.makeText(
            this,
            "${favoriteMovie.title} has removed from favorites",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun addToRecents() {
        // check if already in recent
        runBlocking {
            isRecent = recentViewModel.isRecentMovieExists(movieId)
        }

        // if already in recent then delete it
        if (isRecent) {
            lateinit var recentFilm: RecentFilms
            runBlocking {
                recentFilm = recentViewModel.getRecentMovie(movieId)
            }
            recentViewModel.deleteRecentFilm(recentFilm)
        }

        // crate recent film object
        val id = 0
        val recentId: Int = movieId
        val title: String = movie.title ?: "-"
        val releaseDate: String = movie.releaseDate ?: "-"
        val posterUrl: String = movie.getPosterUrl(ImageSize.W200)
        val rating: Float = movie.rating ?: -1F
        val recentFilm = RecentFilms(
            id,
            movieId = recentId,
            title = title,
            releaseDate = releaseDate,
            posterUrl = posterUrl,
            rating = rating,
        )

        // insert to database
        recentViewModel.addRecentFilm(recentFilm)

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
                if (castList.isNotEmpty()) {
                    castsAdapter = CastsAdapter(castList, this@MovieDetailActivity)
                    binding.rvCastsMovieDetail.adapter = castsAdapter
                } else {
                    binding.tvCastsEmpty.visibility = View.VISIBLE
                }
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
                binding.rvVideosMovieDetail.adapter = videosAdapter
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
                if (movieOutlineList.isNotEmpty()) {
                    similarAdapter = MoviesSecondaryAdapter(movieOutlineList)
                    similarAdapter.setFilmClickListener(this@MovieDetailActivity)
                    binding.rvSimilarMovies.adapter = similarAdapter
                } else {
                    binding.tvSimilarEmpty.visibility = View.VISIBLE
                }
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
                if (movieOutlineList.isNotEmpty()) {
                    recommendationsAdapter = MoviesSecondaryAdapter(movieOutlineList)
                    recommendationsAdapter.setFilmClickListener(this@MovieDetailActivity)
                    binding.rvRecommendationsMovieDetail.adapter = recommendationsAdapter
                } else {
                    binding.tvRecommendationsEmpty.visibility = View.VISIBLE
                }
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@MovieDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setDataToWidgets() {
        // set to widgets
        binding.tvTitleMovieDetail.text = movie.title ?: "-"
        binding.tvReleaseDateMovieDetail.text =
            if (movie.releaseDate != null)
                Utils.convertToDate(movie.releaseDate)
            else
                "-"
        binding.tvRuntimeMovieDetail.text =
            if (movie.runtime != null)
                "${movie.runtime} mins"
            else
                "-"
        binding.tvRatingMovieDetail.text = (movie.rating ?: "-").toString()
        binding.tvSynopsisMovieDetail.text = movie.overview ?: "-"
        Utils.setImageGlide(
            this,
            movie.getWallpaperUrl(ImageSize.W780),
            binding.ivWallpaperMovieDetail, true)
        Utils.setImageGlide(
            this,
            movie.getPosterUrl(ImageSize.W342),
            binding.ivPosterMovieDetail, true)
        Utils.setImageGlide(
            this,
            movie.getPosterUrl(ImageSize.W200),
            binding.ivImgsPostersMovieDetail, true)
        Utils.setImageGlide(
            this,
            movie.getWallpaperUrl(ImageSize.W500),
            binding.ivImgsBackdropsMovieDetail, true)

        // set rv genres
        genresAdapter = GenresAdapter(movie.genreList, this@MovieDetailActivity)
        binding.rvGenreMovieDetail.adapter = genresAdapter

        // remove progress bar
        binding.pbMovieDetail.visibility = View.GONE

        // add to recent films
        addToRecents()
    }


    override fun onGenreClicked(genre: Genre) {}

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
