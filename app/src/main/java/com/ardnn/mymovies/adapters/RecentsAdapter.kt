package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.database.entities.RecentFilms
import com.ardnn.mymovies.databinding.ItemRvRecentsBinding
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.RecentsClickListener

class RecentsAdapter(
    private val recentList: List<RecentFilms>,
    private val clickListener: RecentsClickListener
) : RecyclerView.Adapter<RecentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_recents, parent, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(recentList[position])
    }

    override fun getItemCount(): Int {
        return recentList.size
    }

    inner class ViewHolder(itemView: View, clickListener: RecentsClickListener)
        : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvRecentsBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                clickListener.itemClicked(recentList[absoluteAdapterPosition])
            }
        }

        fun onBind(recentFilm: RecentFilms) {
            with (binding) {
                if (recentFilm.posterUrl != null || recentFilm.posterUrl) {
                    Utils.setImageGlide(
                        itemView.context,
                        recentFilm.posterUrl,
                        ivPosterItemRecents)
                } else {
                    ivPosterItemRecents.setImageResource(R.drawable.img_placeholder)
                }
                ivTypeItemRecents.setImageResource(
                    when {
                        recentFilm.movieId != -1 -> R.drawable.ic_movies_yellow
                        recentFilm.tvShowId != -1 -> R.drawable.ic_tv_shows_yellow
                        else -> R.drawable.ic_recent_yellow
                    }
                )
                tvTitleItemRecents.text = recentFilm.title
                tvYearItemRecents.text = recentFilm.releaseDate.substring(0, 4)
                tvRatingItemRecents.text = recentFilm.rating.toString()
                ivDeleteItemRecents.setOnClickListener {
                    clickListener.btnDeleteClicked(recentFilm)
                }
            }

        }

    }

}