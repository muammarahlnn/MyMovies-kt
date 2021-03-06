package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.databinding.ItemRvFilmsSecondaryBinding
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.FilmDetailClickListener
import com.ardnn.mymovies.listeners.PersonDetailClickListener
import com.ardnn.mymovies.models.ImageSize
import com.ardnn.mymovies.models.MovieOutline

class MoviesSecondaryAdapter(
    private val movieList: MutableList<MovieOutline>
) : RecyclerView.Adapter<MoviesSecondaryAdapter.ViewHolder>() {
    private lateinit var filmClickListener: FilmDetailClickListener
    private lateinit var personClickListener: PersonDetailClickListener

    fun setFilmClickListener(clickListener: FilmDetailClickListener) {
        filmClickListener = clickListener
    }

    fun setPersonClickListener(clickListener: PersonDetailClickListener) {
        personClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_films_secondary, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(movieList[position])
        if (this::filmClickListener.isInitialized) {
            holder.itemView.setOnClickListener {
                filmClickListener.onSimilarClicked(movieList[position])
            }
        }
        if (this::personClickListener.isInitialized) {
            holder.itemView.setOnClickListener {
                personClickListener.onMovieClicked(movieList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvFilmsSecondaryBinding.bind(itemView)

        fun onBind(movieOutline: MovieOutline) {
            with (binding) {
                // set data to widgets
                if (movieOutline.posterUrl != null && movieOutline.posterUrl != "") {
                    Utils.setImageGlide(
                        itemView.context,
                        movieOutline.getPosterUrl(ImageSize.W342),
                        ivPosterItemFilmsSecondary)
                } else {
                    ivPosterItemFilmsSecondary.setImageResource(R.drawable.img_placeholder)
                }
                tvTitleItemFilmsSecondary.text = movieOutline.title ?: "-"
                tvYearItemFilmsSecondary.text =
                    if (movieOutline.releaseDate != null && movieOutline.releaseDate != "")
                        movieOutline.releaseDate.substring(0, 4)
                    else "-"
                tvVoteItemFilmsSecondary.text =
                    if (movieOutline.rating != null) "%.1f".format(movieOutline.rating)
                    else "-"
            }
        }


    }
}