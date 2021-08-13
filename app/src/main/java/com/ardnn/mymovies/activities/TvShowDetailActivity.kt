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
import com.ardnn.mymovies.adapters.TvShowsSecondaryAdapter
import com.ardnn.mymovies.adapters.VideosAdapter
import com.ardnn.mymovies.api.callbacks.*
import com.ardnn.mymovies.api.repositories.TvShowRepository
import com.ardnn.mymovies.database.entities.FavoriteTvShows
import com.ardnn.mymovies.database.entities.RecentFilms
import com.ardnn.mymovies.database.viewmodels.FavoriteFilmViewModel
import com.ardnn.mymovies.database.viewmodels.RecentFilmViewModel
import com.ardnn.mymovies.databinding.ActivityTvShowDetailBinding
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.FilmDetailClickListener
import com.ardnn.mymovies.models.*
import kotlinx.coroutines.runBlocking

class TvShowDetailActivity : AppCompatActivity(), View.OnClickListener, FilmDetailClickListener {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    // view model
    private lateinit var favoriteViewModel: FavoriteFilmViewModel
    private lateinit var recentViewModel: RecentFilmViewModel

    // view binding
    private lateinit var binding: ActivityTvShowDetailBinding

    // tv show
    private lateinit var tvShow: TvShow
    private var tvShowId: Int = 0

    // adapters
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var castsAdapter: CastsAdapter
    private lateinit var videosAdapter: VideosAdapter
    private lateinit var similarAdapter: TvShowsSecondaryAdapter
    private lateinit var recommendationsAdapter: TvShowsSecondaryAdapter

    // variables
    private var isSynopsisExtended: Boolean = false
    private var isFavorite: Boolean = false
    private var isRecent: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        // initialization
        initialization()

        // load tv show data
        loadTvShowData()

        // if button clicked
        binding.btnBackTvShowDetail.setOnClickListener(this)
        binding.btnFavoriteTvShowDetail.setOnClickListener(this)
        binding.btnHomeTvShowDetail.setOnClickListener(this)
        binding.clWrapperSynopsisTvShowDetail.setOnClickListener(this)
        binding.ivImgsPostersTvShowDetail.setOnClickListener(this)
        binding.ivImgsBackdropsTvShowDetail.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        // images detail intent
        val goToImagesDetail = Intent(this, ImagesDetailActivity::class.java)
        goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_ID, tvShowId)
        goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_ACTIVITY_KEY, ImagesDetailActivity.TV_SHOW)

        when (v?.id) {
            R.id.btn_back_tv_show_detail -> {
                finish()
            }
            R.id.btn_favorite_tv_show_detail -> {
                isFavorite = !isFavorite
                if (isFavorite) { // like
                    addTvShowToDatabase()
                } else { // dislike
                    deleteTvShowFromDatabase()
                }
            }
            R.id.cl_wrapper_synopsis_tv_show_detail -> {
                isSynopsisExtended = !isSynopsisExtended
                if (isSynopsisExtended) {
                    binding.tvSynopsisTvShowDetail.maxLines = Int.MAX_VALUE
                    binding.tvMoreTvShowDetail.text = "less"
                } else {
                    binding.tvSynopsisTvShowDetail.maxLines = 2
                    binding.tvMoreTvShowDetail.text = "more"
                }
            }
            R.id.iv_imgs_posters_tv_show_detail -> {
                goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_IMAGES_KEY, ImagesDetailActivity.POSTERS)
                startActivity(goToImagesDetail)
            }
            R.id.iv_imgs_backdrops_tv_show_detail -> {
                goToImagesDetail.putExtra(ImagesDetailActivity.EXTRA_IMAGES_KEY, ImagesDetailActivity.BACKDROPS)
                startActivity(goToImagesDetail)
            }
            R.id.btn_home_tv_show_detail -> {
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

        // tv show
        tvShowId = intent.getIntExtra(EXTRA_ID, 0)

        // genres
        binding.rvGenreTvShowDetail.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

        // casts
        binding.rvCastsTvShowDetail.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

        // videos
        binding.rvVideosTvShowDetail.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

        // similar tv shows
        binding.rvSimilarTvShows.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

        // recommendations
        binding.rvRecommendationsTvShowDetail.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

        // set btn favorite
        runBlocking {
            isFavorite = favoriteViewModel.isTvShowExists(tvShowId)
        }
        binding.btnFavoriteTvShowDetail.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_true else R.drawable.ic_favorite_false)

    }

    private fun addTvShowToDatabase() {
        // create favorite tv show object
        val id: Int = tvShowId
        val title: String = tvShow.title ?: "-"
        val releaseDate: String = tvShow.firstAirDate ?: "-"
        val posterUrl: String = tvShow.getPosterUrl(ImageSize.W200)
        val rating: Float = tvShow.rating ?: -1F
        val favoriteTvShow = FavoriteTvShows(id, title, releaseDate, posterUrl, rating)

        // insert to database
        favoriteViewModel.addTvShow(favoriteTvShow)

        // set icon favorite to true and notify the user
        binding.btnFavoriteTvShowDetail.setImageResource(R.drawable.ic_favorite_true)
        Toast.makeText(this, "$title has added to favorites", Toast.LENGTH_SHORT).show()
    }

    private fun deleteTvShowFromDatabase() {
        // get tv show from database
        lateinit var favoriteTvShow: FavoriteTvShows
        runBlocking {
            favoriteTvShow = favoriteViewModel.getTvShow(tvShowId)
        }

        // delete it from database
        favoriteViewModel.deleteTvShow(favoriteTvShow)

        // set icon favorite to false and notify the user
        binding.btnFavoriteTvShowDetail.setImageResource(R.drawable.ic_favorite_false)
        Toast.makeText(
            this,
            "${favoriteTvShow.title} has removed from favorites",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun addToRecents() {
        // check if already in recent
        runBlocking {
            isRecent = recentViewModel.isRecentTvShowExists(tvShowId)
        }

        // if already in recent then delete it
        if (isRecent) {
            lateinit var recentFilm: RecentFilms
            runBlocking {
                recentFilm = recentViewModel.getRecentTvShow(tvShowId)
            }
            recentViewModel.deleteRecentFilm(recentFilm)
        }

        // create recent film object
        val id = 0
        val recentId: Int = tvShowId
        val title: String = tvShow.title ?: "-"
        val releaseDate: String = tvShow.firstAirDate ?: "-"
        val posterUrl: String = tvShow.getPosterUrl(ImageSize.W200)
        val rating: Float = tvShow.rating ?: -1F
        val recentFilms = RecentFilms(
            id,
            tvShowId = recentId,
            title = title,
            releaseDate = releaseDate,
            posterUrl = posterUrl,
            rating = rating
        )

        // insert to database
        recentViewModel.addRecentFilm(recentFilms)
    }

    private fun loadTvShowData() {
        loadTvShowDetails()
        loadTvShowCasts()
        loadTvShowVideos()
        loadSimilarTvShows()
        loadTvShowRecommendations()
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
        TvShowRepository.getTvShowCasts(tvShowId, object : CastsCallback {
            override fun onSuccess(castList: List<Cast>) {
                // set recyclerview casts
                castsAdapter = CastsAdapter(castList, this@TvShowDetailActivity)
                binding.rvCastsTvShowDetail.adapter = castsAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@TvShowDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadTvShowVideos() {
        TvShowRepository.getTvShowVideos(tvShowId, object : VideosCallback {
            override fun onSuccess(videoList: List<Video>) {
                // setup recyclerview videos
                videosAdapter = VideosAdapter(videoList, this@TvShowDetailActivity)
                binding.rvVideosTvShowDetail.adapter = videosAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@TvShowDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadSimilarTvShows() {
        TvShowRepository.getSimilarTvShows(tvShowId, object : TvShowOutlineCallback {
            override fun onSuccess(tvShowOutlineList: MutableList<TvShowOutline>) {
                // setup recyclerview similar tv shows
                similarAdapter = TvShowsSecondaryAdapter(tvShowOutlineList)
                similarAdapter.setFilmClickListener(this@TvShowDetailActivity)
                binding.rvSimilarTvShows.adapter = similarAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@TvShowDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadTvShowRecommendations() {
        TvShowRepository.getTvShowRecommendations(tvShowId, object : TvShowOutlineCallback {
            override fun onSuccess(tvShowOutlineList: MutableList<TvShowOutline>) {
                // setup recyclerview recommendations
                recommendationsAdapter = TvShowsSecondaryAdapter(tvShowOutlineList)
                recommendationsAdapter.setFilmClickListener(this@TvShowDetailActivity)
                binding.rvRecommendationsTvShowDetail.adapter = recommendationsAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@TvShowDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setDataToWidgets() {
        // set to widgets
        binding.tvTitleTvShowDetail.text = tvShow.title ?: "-"
        binding.tvEpisodesTvShowDetail.text = (tvShow.numberOfEpisodes ?: "-").toString()
        binding.tvSeasonsTvShowDetail.text = (tvShow.numberOfSeasons ?: "-").toString()
        binding.tvRuntimeTvShowDetail.text =
            if (tvShow.runtimes != null && tvShow.runtimes?.size != 0)
                "${tvShow.runtimes?.get(0)} mins"
            else
                "-"
        binding.tvRatingTvShowDetail.text = (tvShow.rating ?: "-").toString()
        binding.tvFirstAiring.text =
            if (tvShow.firstAirDate != null)
                Utils.convertToDate(tvShow.firstAirDate)
            else
                "-"
        binding.tvLastAiring.text =
            if (tvShow.lastAirDate != null)
                Utils.convertToDate(tvShow.lastAirDate)
            else
                "-"
        binding.tvSynopsisTvShowDetail.text = tvShow.overview ?: "-"
        Utils.setImageGlide(
            this,
            tvShow.getWallpaperUrl(ImageSize.W780),
            binding.ivWallpaperTvShowDetail, true)
        Utils.setImageGlide(
            this,
            tvShow.getPosterUrl(ImageSize.W342),
            binding.ivPosterTvShowDetail, true)
        Utils.setImageGlide(
            this,
            tvShow.getPosterUrl(ImageSize.W342),
            binding.ivImgsPostersTvShowDetail, true)
        Utils.setImageGlide(
            this,
            tvShow.getWallpaperUrl(ImageSize.W780),
            binding.ivImgsBackdropsTvShowDetail, true)

        // set rv genres
        genresAdapter = GenresAdapter(tvShow.genreList, this)
        binding.rvGenreTvShowDetail.adapter = genresAdapter

        // remove progress bar
        binding.pbTvShowDetail.visibility = View.GONE

        // add to recent films
        addToRecents()

    }
    override fun onGenreClicked(genre: Genre) {}

    override fun onCastClicked(cast: Cast) {
        // go to person detail
        val goToPersonDetail = Intent(this, PersonDetailActivity::class.java)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_ID, cast.id)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_KNOWN_AS, cast.character)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_FILM, tvShow.title)
        goToPersonDetail.putExtra(PersonDetailActivity.EXTRA_WALLPAPER_URL, tvShow.getWallpaperUrl(ImageSize.W780))
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

    override fun onSimilarClicked(tvShowOutline: TvShowOutline) {
        // go to tv show detail
        val goToTvShowDetail = Intent(this, TvShowDetailActivity::class.java)
        goToTvShowDetail.putExtra(EXTRA_ID, tvShowOutline.id)
        startActivity(goToTvShowDetail)
    }

    override fun onSimilarClicked(movieOutline: MovieOutline) {
        // do nothing
    }
}
