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
import com.ardnn.mymovies.models.TvShowOutline

class TvShowsSecondaryAdapter(
    private val tvShowList: MutableList<TvShowOutline>
) : RecyclerView.Adapter<TvShowsSecondaryAdapter.ViewHolder>(){
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
        holder.onBind(tvShowList[position])
        if (this::filmClickListener.isInitialized) {
            holder.itemView.setOnClickListener {
                filmClickListener.onSimilarClicked(tvShowList[position])
            }
        }
        if (this::personClickListener.isInitialized) {
            holder.itemView.setOnClickListener {
                personClickListener.onTvShowClicked(tvShowList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvFilmsSecondaryBinding.bind(itemView)

        fun onBind(tvShowOutline: TvShowOutline) {
            with (binding) {
                // set data to widgets
                if (tvShowOutline.posterUrl != null && tvShowOutline.posterUrl != "") {
                    Utils.setImageGlide(
                        itemView.context,
                        tvShowOutline.getPosterUrl(ImageSize.W342),
                        ivPosterItemFilmsSecondary)
                } else {
                    ivPosterItemFilmsSecondary.setImageResource(R.drawable.img_placeholder)
                }
                tvTitleItemFilmsSecondary.text = tvShowOutline.title ?: "-"
                tvYearItemFilmsSecondary.text =
                    if (tvShowOutline.releaseDate != null && tvShowOutline.releaseDate != "")
                        tvShowOutline.releaseDate.substring(0, 4)
                    else "-"
                tvVoteItemFilmsSecondary.text =
                    if (tvShowOutline.rating != null) "%.1f".format(tvShowOutline.rating)
                    else "-"

            }
        }

    }

}