package com.ardnn.mymovies.activities

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.mymovies.R
import com.ardnn.mymovies.adapters.ImagesPagerAdapter
import com.ardnn.mymovies.api.callbacks.ImagesCallback
import com.ardnn.mymovies.api.repositories.MovieRepository
import com.ardnn.mymovies.models.Image

class ImagesDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_KEY = "extra_key"
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
    private var movieId: Int = 0
    private var imagesKey: String = ""
    private var isToolbarActive: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_detail)

        // initialization
        imagesPagerAdapter = ImagesPagerAdapter()
        movieId = intent.getIntExtra(EXTRA_ID, 0)
        imagesKey = intent.getStringExtra(EXTRA_KEY) ?: ""
        imagesPager = findViewById(R.id.vp2_images_detail)
        clToolbar = findViewById(R.id.cl_toolbar_images_detail)
        btnBack = findViewById(R.id.btn_back_images_detail)
        tvCurrentImage = findViewById(R.id.tv_current_image)
        tvTotalImage = findViewById(R.id.tv_total_image)

        // load data
        loadImagesData()

        // if viewpager item change
        imagesPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tvCurrentImage.text = (position + 1).toString()
            }
        })

        // if image clicked
        imagesPagerAdapter.setOnImageClick(object : ImagesPagerAdapter.OnImageClick {
            override fun imageClicked() {
                isToolbarActive = !isToolbarActive
                clToolbar.visibility = if (isToolbarActive) View.VISIBLE else View.GONE
            }
        })

        // if widget clicked
        btnBack.setOnClickListener {
            finish()
        }

    }

    private fun loadImagesData() {
        MovieRepository.getMovieImages(movieId, object : ImagesCallback {
            override fun onPostersSuccess(posterList: List<Image>) {
                if (imagesKey == POSTERS) {
                    // debug
                    for (poster in posterList) {
                        println("POSTER -> ${poster.imageUrl ?: "null"}")
                    }

                    // set data to widgets
                    setDataToWidgets(posterList)

                    // set screen orientation
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
            }

            override fun onBackdropsSuccess(backdropList: List<Image>) {
                if (imagesKey == BACKDROPS) {
                    // debug
                    for (backdrop in backdropList) {
                        println("BACKDROP -> ${backdrop.imageUrl ?: "null"}")
                    }

                    // set data to widgets
                    setDataToWidgets(backdropList)

                    // set screen orientation
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
            }

            override fun onFailure(message: String) {
                Toast.makeText(this@ImagesDetailActivity, message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setDataToWidgets(imageList: List<Image>) {
        // viewpager
        imagesPagerAdapter.setImageList(imageList)
        imagesPager.adapter = imagesPagerAdapter
        imagesPager.currentItem = 0

        // widgets
        tvCurrentImage.text = "1"
        tvTotalImage.text = imageList.size.toString()
    }

}