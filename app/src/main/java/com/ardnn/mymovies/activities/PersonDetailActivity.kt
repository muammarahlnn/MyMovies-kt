package com.ardnn.mymovies.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.AlsoKnownAsAdapter
import com.ardnn.mymovies.adapters.MoviesSecondaryAdapter
import com.ardnn.mymovies.adapters.TvShowsSecondaryAdapter
import com.ardnn.mymovies.api.callbacks.MovieOutlineCallback
import com.ardnn.mymovies.api.callbacks.PersonDetailsCallback
import com.ardnn.mymovies.api.callbacks.TvShowOutlineCallback
import com.ardnn.mymovies.api.repositories.PersonRepository
import com.ardnn.mymovies.databinding.ActivityPersonDetailBinding
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.PersonDetailClickListener
import com.ardnn.mymovies.models.*

class PersonDetailActivity : AppCompatActivity(), View.OnClickListener, PersonDetailClickListener {
    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_KNOWN_AS = "extra_known_as"
        const val EXTRA_FILM = "extra_film"
        const val EXTRA_WALLPAPER_URL = "extra_wallpaper_url"
    }

    // view binding
    private lateinit var binding: ActivityPersonDetailBinding

    // person
    private lateinit var person: Person
    private var personId: Int = 0

    // adapters
    private lateinit var akaAdapter: AlsoKnownAsAdapter
    private lateinit var moviesAdapter: MoviesSecondaryAdapter
    private lateinit var tvShowsAdapter: TvShowsSecondaryAdapter

    // variables
    private var isBiographyExtended: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialization
        initialization()

        // if button clicked
        binding.btnBackPersonDetail.setOnClickListener(this)
        binding.btnHomePersonDetail.setOnClickListener(this)
        binding.clWrapperBiographyPersonDetail.setOnClickListener(this)

        // load person detail data
        loadData()
        loadMoviesData()
        loadTvShowData()
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back_person_detail -> {
                finish()
            }
            R.id.cl_wrapper_biography_person_detail -> {
                isBiographyExtended = !isBiographyExtended
                if (isBiographyExtended) {
                    binding.tvBiographyPersonDetail.maxLines = Int.MAX_VALUE
                    binding.tvMorePersonDetail.text = "less"
                } else {
                    binding.tvBiographyPersonDetail.maxLines = 2
                    binding.tvMorePersonDetail.text = "more"
                }
            }
            R.id.btn_home_person_detail -> {
                // go to home and remove all activity
                val goToHome = Intent(this, MainActivity::class.java)
                goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(goToHome)
            }
        }
    }

    private fun initialization() {
        // person
        personId = intent.getIntExtra(EXTRA_ID, 0)

        // also known as
        binding.rvAkaPersonDetail.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // movies
        binding.rvMoviesPersonDetail.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // tv shows
        binding.rvTvShowsPersonDetail.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun loadData() {
        PersonRepository.getPersonDetails(personId, object : PersonDetailsCallback {
            override fun onSuccess(person: Person) {
                // get person details and set it to widgets
                this@PersonDetailActivity.person = person
                setDataToWidgets()
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@PersonDetailActivity, message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun loadMoviesData() {
        PersonRepository.getPersonMovies(personId, object : MovieOutlineCallback {
            override fun onSuccess(movieOutlineList: MutableList<MovieOutline>) {
                for (movie in movieOutlineList) {
                    Log.d("PERSON MOVIE", movie.title ?: "-")
                }

                // setup recyclerview movies
                moviesAdapter = MoviesSecondaryAdapter(movieOutlineList)
                moviesAdapter.setPersonClickListener(this@PersonDetailActivity)
                binding.rvMoviesPersonDetail.adapter = moviesAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@PersonDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadTvShowData() {
        PersonRepository.getPersonTvShows(personId, object : TvShowOutlineCallback {
            override fun onSuccess(tvShowOutlineList: MutableList<TvShowOutline>) {
                for (tvShow in tvShowOutlineList) {
                    Log.d("PERSON TV", tvShow.title ?: "-")
                }

                // setup recyclerview tv shows
                tvShowsAdapter = TvShowsSecondaryAdapter(tvShowOutlineList)
                tvShowsAdapter.setPersonClickListener(this@PersonDetailActivity)
                binding.rvTvShowsPersonDetail.adapter = tvShowsAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@PersonDetailActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setDataToWidgets() {
        binding.tvNamePersonDetail.text = person.name ?: "-"
        binding.tvKnownAsPersonDetail.text = intent.getStringExtra(EXTRA_KNOWN_AS)
        binding.tvFilmPersonDetail.text = intent.getStringExtra(EXTRA_FILM)
        binding.tvDepartmentPersonDetail.text = person.department ?: "-"
        binding.tvBornInPersonDetail.text = person.birthPlace ?: "-"
        binding.tvBornOnPersonDetail.text =
            if (person.birthday != null)
                Utils.convertToDate(person.birthday)
            else
                "-"
        binding.tvBiographyPersonDetail.text = person.biography ?: "-"
        Utils.setImageGlide(
            this,
            person.getProfileUrl(ImageSize.W342),
            binding.ivProfilePersonDetail, true)
        Utils.setImageGlide(
            this,
            intent.getStringExtra(EXTRA_WALLPAPER_URL),
            binding.ivWallpaperPersonDetail, true)

        // set rv also known as
        akaAdapter = AlsoKnownAsAdapter(person.akaList)
        binding.rvAkaPersonDetail.adapter = akaAdapter
        if (person.akaList.isEmpty()) {
            binding.tvAkaGonePersonDetail.visibility = View.VISIBLE
        }

        // remove progress bar
        binding.pbPersonDetail.visibility = View.GONE
    }

    override fun onMovieClicked(movie: MovieOutline) {
        // go to movie detail
        val goToMovieDetail = Intent(this, MovieDetailActivity::class.java)
        goToMovieDetail.putExtra(MovieDetailActivity.EXTRA_ID, movie.id)
        startActivity(goToMovieDetail)
    }

    override fun onTvShowClicked(tvShow: TvShowOutline) {
        // go to tv show detail
        val goToTvShowDetail = Intent(this, TvShowDetailActivity::class.java)
        goToTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_ID, tvShow.id)
        startActivity(goToTvShowDetail)
    }

}