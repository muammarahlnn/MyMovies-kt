package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.database.entities.RecentFilms
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
        private val ivPoster: ImageView = itemView.findViewById(R.id.iv_poster_item_recents)
        private val ivType: ImageView = itemView.findViewById(R.id.iv_type_item_recents)
        private val ivDelete: ImageView = itemView.findViewById(R.id.iv_delete_item_recents)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title_item_recents)
        private val tvYear: TextView = itemView.findViewById(R.id.tv_year_item_recents)
        private val tvRating: TextView = itemView.findViewById(R.id.tv_rating_item_recents)

        init {
            itemView.setOnClickListener {
                clickListener.itemClicked(recentList[absoluteAdapterPosition])
            }
        }

        fun onBind(recentFilm: RecentFilms) {
            if (recentFilm.posterUrl != null || recentFilm.posterUrl) {
                Utils.setImageGlide(
                    itemView.context,
                    recentFilm.posterUrl,
                    ivPoster)
            } else {
                ivPoster.setImageResource(R.drawable.img_placeholder)
            }
            ivType.setImageResource(
                when {
                    recentFilm.movieId != -1 -> R.drawable.ic_movies_yellow
                    recentFilm.tvShowId != -1 -> R.drawable.ic_tv_shows_yellow
                    else -> R.drawable.ic_recent_yellow
                }
            )
            tvTitle.text = recentFilm.title
            tvYear.text = recentFilm.releaseDate.substring(0, 4)
            tvRating.text = recentFilm.rating.toString()
            ivDelete.setOnClickListener {
                clickListener.btnDeleteClicked(recentFilm)
            }

        }

    }

}