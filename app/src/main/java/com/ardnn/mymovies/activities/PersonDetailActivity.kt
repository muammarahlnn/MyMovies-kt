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
import com.ardnn.mymovies.adapters.AlsoKnownAsAdapter
import com.ardnn.mymovies.api.callbacks.movies.MovieOutlineCallback
import com.ardnn.mymovies.api.callbacks.person.PersonDetailsCallback
import com.ardnn.mymovies.api.callbacks.tvshows.TvShowOutlineCallback
import com.ardnn.mymovies.api.repositories.PersonRepository
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.ImageSize
import com.ardnn.mymovies.models.MovieOutline
import com.ardnn.mymovies.models.Person
import com.ardnn.mymovies.models.TvShowOutline
import com.bumptech.glide.Glide

class PersonDetailActivity : AppCompatActivity(), View.OnClickListener {
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
        Glide.with(this)
            .load(person.getProfileUrl(ImageSize.W342))
            .into(ivProfile)
        Glide.with(this)
            .load(intent.getStringExtra(EXTRA_WALLPAPER_URL))
            .into(ivWallpaper)

        // set rv also known as
        akaAdapter = AlsoKnownAsAdapter(person.akaList)
        rvAka.adapter = akaAdapter
        if (person.akaList.isEmpty()) {
            tvAkaGone.visibility = View.VISIBLE
        }

        // remove progress bar
        progressBar.visibility = View.GONE
    }

}