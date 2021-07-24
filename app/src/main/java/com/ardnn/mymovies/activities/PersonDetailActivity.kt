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
import com.ardnn.mymovies.adapters.AlsoKnownAsAdapter
import com.ardnn.mymovies.adapters.MoviesSecondaryAdapter
import com.ardnn.mymovies.adapters.TvShowsSecondaryAdapter
import com.ardnn.mymovies.api.callbacks.MovieOutlineCallback
import com.ardnn.mymovies.api.callbacks.PersonDetailsCallback
import com.ardnn.mymovies.api.callbacks.TvShowOutlineCallback
import com.ardnn.mymovies.api.repositories.PersonRepository
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.FilmDetailClickListener
import com.ardnn.mymovies.listeners.PersonDetailClickListener
import com.ardnn.mymovies.models.*
import com.google.android.material.button.MaterialButton

class PersonDetailActivity : AppCompatActivity(), View.OnClickListener, PersonDetailClickListener {
    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_KNOWN_AS = "extra_known_as"
        const val EXTRA_FILM = "extra_film"
        const val EXTRA_WALLPAPER_URL = "extra_wallpaper_url"
    }

    // person
    private lateinit var person: Person
    private var personId: Int = 0

    // rv also known as
    private lateinit var rvAka: RecyclerView
    private lateinit var akaAdapter: AlsoKnownAsAdapter

    // movies
    private lateinit var rvMovies: RecyclerView
    private lateinit var moviesAdapter: MoviesSecondaryAdapter

    // tv shows
    private lateinit var rvTvShows: RecyclerView
    private lateinit var tvShowsAdapter: TvShowsSecondaryAdapter

    // widgets
    private lateinit var tvName: TextView
    private lateinit var tvKnownAs: TextView
    private lateinit var tvFilm: TextView
    private lateinit var tvDepartment: TextView
    private lateinit var tvAkaGone: TextView
    private lateinit var tvBornOn: TextView
    private lateinit var tvBornIn: TextView
    private lateinit var tvBiography: TextView
    private lateinit var tvMore: TextView
    private lateinit var ivProfile: ImageView
    private lateinit var ivWallpaper: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var btnHome: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var clWrapperBiography: ConstraintLayout

    // variables
    private var isBiographyExtended: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)

        // initialization
        initialization()

        // if button clicked
        btnBack.setOnClickListener(this)
        btnHome.setOnClickListener(this)
        clWrapperBiography.setOnClickListener(this)

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
                    tvBiography.maxLines = Int.MAX_VALUE
                    tvMore.text = "less"
                } else {
                    tvBiography.maxLines = 2
                    tvMore.text = "more"
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
        rvAka = findViewById(R.id.rv_aka_person_detail)
        rvAka.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // movies
        rvMovies = findViewById(R.id.rv_movies_person_detail)
        rvMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // tv shows
        rvTvShows = findViewById(R.id.rv_tv_shows_person_detail)
        rvTvShows.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // widgets
        tvName = findViewById(R.id.tv_name_person_detail)
        tvKnownAs = findViewById(R.id.tv_known_as_person_detail)
        tvFilm = findViewById(R.id.tv_film_person_detail)
        tvDepartment = findViewById(R.id.tv_department_person_detail)
        tvAkaGone = findViewById(R.id.tv_aka_gone_person_detail)
        tvBornOn = findViewById(R.id.tv_born_on_person_detail)
        tvBornIn = findViewById(R.id.tv_born_in_person_detail)
        tvBiography = findViewById(R.id.tv_biography_person_detail)
        tvMore = findViewById(R.id.tv_more_person_detail)
        ivProfile = findViewById(R.id.iv_profile_person_detail)
        ivWallpaper = findViewById(R.id.iv_wallpaper_person_detail)
        clWrapperBiography = findViewById(R.id.cl_wrapper_biography_person_detail)
        btnBack = findViewById(R.id.btn_back_person_detail)
        btnHome = findViewById(R.id.btn_home_person_detail)
        progressBar = findViewById(R.id.pb_person_detail)
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
                rvMovies.adapter = moviesAdapter
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
                rvTvShows.adapter = tvShowsAdapter
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@PersonDetailActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setDataToWidgets() {
        tvName.text = person.name ?: "-"
        tvKnownAs.text = intent.getStringExtra(EXTRA_KNOWN_AS)
        tvFilm.text = intent.getStringExtra(EXTRA_FILM)
        tvDepartment.text = person.department ?: "-"
        tvBornIn.text = person.birthPlace ?: "-"
        tvBornOn.text =
            if (person.birthday != null)
                Utils.convertToDate(person.birthday)
            else
                "-"
        tvBiography.text = person.biography ?: "-"
        Utils.setImageGlide(
            this,
            person.getProfileUrl(ImageSize.W342),
            ivProfile, true)
        Utils.setImageGlide(
            this,
            intent.getStringExtra(EXTRA_WALLPAPER_URL),
            ivWallpaper, true)

        // set rv also known as
        akaAdapter = AlsoKnownAsAdapter(person.akaList)
        rvAka.adapter = akaAdapter
        if (person.akaList.isEmpty()) {
            tvAkaGone.visibility = View.VISIBLE
        }

        // remove progress bar
        progressBar.visibility = View.GONE
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