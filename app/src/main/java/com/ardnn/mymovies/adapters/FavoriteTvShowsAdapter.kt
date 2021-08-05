package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.database.entities.FavoriteTvShows
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.SingleClickListener

class FavoriteTvShowsAdapter(
    private val favoriteTvShowList: List<FavoriteTvShows>,
    private val clickListener: SingleClickListener<FavoriteTvShows>
) : RecyclerView.Adapter<FavoriteTvShowsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_favorite, parent, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(favoriteTvShowList[position])
    }

    override fun getItemCount(): Int {
        return favoriteTvShowList.size
    }


    inner class ViewHolder(itemView: View, clickListener: SingleClickListener<FavoriteTvShows>)
        : RecyclerView.ViewHolder(itemView) {
        private val ivPoster: ImageView = itemView.findViewById(R.id.iv_poster_item_favorite)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title_item_favorite)
        private val tvYear: TextView = itemView.findViewById(R.id.tv_year_item_favorite)
        private val tvRating: TextView = itemView.findViewById(R.id.tv_rating_item_favorite)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClicked(favoriteTvShowList[absoluteAdapterPosition])
            }
        }

        fun onBind(favoriteTvShow: FavoriteTvShows) {
            if (favoriteTvShow.posterUrl != null || favoriteTvShow.posterUrl == "") {
                Utils.setImageGlide(
                    itemView.context,
                    favoriteTvShow.posterUrl,
                    ivPoster)
            } else {
                ivPoster.setImageResource(R.drawable.img_placeholder)
            }
            tvTitle.text = favoriteTvShow.title
            tvYear.text = favoriteTvShow.releaseDate.substring(0, 4)
            tvRating.text = favoriteTvShow.rating.toString()
        }
    }
}