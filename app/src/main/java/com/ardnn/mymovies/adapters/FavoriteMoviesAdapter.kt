package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.SingleClickListener

class FavoriteMoviesAdapter(
    private val favoriteMovieList: List<FavoriteMovies>,
    private val clickListener: SingleClickListener<FavoriteMovies>
) : RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_favorite, parent, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(favoriteMovieList[position])
    }

    override fun getItemCount(): Int {
        return favoriteMovieList.size
    }

    inner class ViewHolder(itemView: View, clickListener: SingleClickListener<FavoriteMovies>)
        : RecyclerView.ViewHolder(itemView) {
        private val ivPoster: ImageView = itemView.findViewById(R.id.iv_poster_item_favorite)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title_item_favorite)
        private val tvYear: TextView = itemView.findViewById(R.id.tv_year_item_favorite)
        private val tvRating: TextView = itemView.findViewById(R.id.tv_rating_item_favorite)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClicked(favoriteMovieList[absoluteAdapterPosition])
            }
        }

        fun onBind(favoriteMovie: FavoriteMovies) {
            if (favoriteMovie.posterUrl != null || favoriteMovie.posterUrl == "") {
                Utils.setImageGlide(
                    itemView.context,
                    favoriteMovie.posterUrl,
                    ivPoster)
            } else {
                ivPoster.setImageResource(R.drawable.img_placeholder)
            }
            tvTitle.text = favoriteMovie.title
            tvYear.text = favoriteMovie.releaseDate.substring(0, 4)
            tvRating.text = favoriteMovie.rating.toString()
        }
    }

}