package com.ardnn.mymovies.activities

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.ImagesPagerAdapter
import com.ardnn.mymovies.api.callbacks.ImagesCallback
import com.ardnn.mymovies.api.repositories.MovieRepository
import com.ardnn.mymovies.api.repositories.TvShowRepository
import com.ardnn.mymovies.listeners.SingleClickListener
import com.ardnn.mymovies.models.Image

class ImagesDetailActivity : AppCompatActivity(), SingleClickListener<Image> {
    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_ACTIVITY_KEY = "extra_activity_key"
        const val EXTRA_IMAGES_KEY = "extra_images_key"
        const val MOVIE = "movie"
        const val TV_SHOW = "tv_show"
        const val POSTERS = "posters"
        const val BACKDROPS = "backdrops"
    }

    // viewpager attr
    private lateinit var imagesPager: ViewPager2
    private lateinit var imagesPagerAdapter: ImagesPagerAdapter

    // widgets
    private lateinit var clToolbar: ConstraintLayout
    private lateinit var btnBack: ImageView
    private lateinit var tvCurrentImage: TextView
    private lateinit var tvTotalImage: TextView

    // variables
    private var filmId: Int = 0
    private var imagesKey: String = ""
    private var activityKey: String = ""
    private var isToolbarActive: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_detail)

        // initialization
        filmId = intent.getIntExtra(EXTRA_ID, 0)
        activityKey = intent.getStringExtra(EXTRA_ACTIVITY_KEY) ?: ""
        imagesKey = intent.getStringExtra(EXTRA_IMAGES_KEY) ?: ""
        imagesPager = findViewById(R.id.vp2_images_detail)
        clToolbar = findViewById(R.id.cl_toolbar_images_detail)
        btnBack = findViewById(R.id.btn_back_images_detail)
        tvCurrentImage = findViewById(R.id.tv_current_image)
        tvTotalImage = findViewById(R.id.tv_total_image)

        // set screen orientation
        if (imagesKey == POSTERS) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else if (imagesKey == BACKDROPS) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // load data
        loadImagesData()

        // if viewpager item change
        imagesPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tvCurrentImage.text = (position + 1).toString()
            }
        })

        // if widget clicked
        btnBack.setOnClickListener {
            finish()
        }

    }

    private fun loadImagesData() {
        if (activityKey == MOVIE) {
            loadMovieImages()
        } else if (activityKey == TV_SHOW) {
            loadTvShowImages()
        }
    }

    private fun loadMovieImages() {
        MovieRepository.getMovieImages(filmId, object : ImagesCallback {
            override fun onPostersSuccess(posterList: List<Image>) {
                postersSuccessfullyLoaded(posterList)
            }

            override fun onBackdropsSuccess(backdropList: List<Image>) {
                backdropsSuccessfullyLoaded(backdropList)
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@ImagesDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadTvShowImages() {
        TvShowRepository.getTvShowImages(filmId, object : ImagesCallback {
            override fun onPostersSuccess(posterList: List<Image>) {
                postersSuccessfullyLoaded(posterList)
            }

            override fun onBackdropsSuccess(backdropList: List<Image>) {
                backdropsSuccessfullyLoaded(backdropList)
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@ImagesDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun postersSuccessfullyLoaded(posterList: List<Image>) {
        if (imagesKey == POSTERS) {
            // debug
            for (poster in posterList) {
                println("POSTER -> ${poster.imageUrl ?: "null"}")
            }

            // set data to widgets
            setDataToWidgets(posterList)
        }
    }

    private fun backdropsSuccessfullyLoaded(backdropList: List<Image>) {
        if (imagesKey == BACKDROPS) {
            // debug
            for (backdrop in backdropList) {
                println("BACKDROP -> ${backdrop.imageUrl ?: "null"}")
            }

            // set data to widgets
            setDataToWidgets(backdropList)
        }
    }

    private fun setDataToWidgets(imageList: List<Image>) {
        // viewpager
        imagesPagerAdapter = ImagesPagerAdapter(imageList, this)
        imagesPager.adapter = imagesPagerAdapter
        imagesPager.currentItem = 0

        // widgets
        tvCurrentImage.text = "1"
        tvTotalImage.text = imageList.size.toString()
    }

    override fun onItemClicked(item: Image) {
        isToolbarActive = !isToolbarActive
        clToolbar.visibility = if (isToolbarActive) View.VISIBLE else View.GONE
    }

}