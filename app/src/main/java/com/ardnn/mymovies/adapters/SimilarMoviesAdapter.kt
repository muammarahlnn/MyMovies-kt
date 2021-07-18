package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.FilmDetailClickListener
import com.ardnn.mymovies.models.ImageSize
import com.ardnn.mymovies.models.MovieOutline

class SimilarMoviesAdapter(
    private val similarList: MutableList<MovieOutline>,
    private val clickListener: FilmDetailClickListener
) : RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_similar, parent, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(similarList[position])
    }

    override fun getItemCount(): Int {
        return similarList.size
    }

    inner class ViewHolder(itemView: View,clickListener: FilmDetailClickListener)
        : RecyclerView.ViewHolder(itemView) {
        private val ivPoster: ImageView = itemView.findViewById(R.id.iv_poster_item_similar)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title_item_similar)
        private val tvYear: TextView = itemView.findViewById(R.id.tv_year_item_similar)
        private val tvVote: TextView = itemView.findViewById(R.id.tv_vote_item_similar)

        init {
            itemView.setOnClickListener {
                clickListener.onSimilarClicked(similarList[absoluteAdapterPosition])
            }
        }

        fun onBind(movieOutline: MovieOutline) {
            // set data to widgets
            if (movieOutline.posterUrl != null || movieOutline.posterUrl == "") {
                Utils.setImageGlide(
                    itemView.context,
                    movieOutline.getPosterUrl(ImageSize.W342),
                    ivPoster)
            } else {
                ivPoster.setImageResource(R.drawable.img_placeholder)
            }
            tvTitle.text = movieOutline.title ?: "-"
            tvYear.text =
                if (movieOutline.releaseDate == null || movieOutline.releaseDate == "") "-"
                else movieOutline.releaseDate.substring(0, 4)
            tvVote.text = "%.1f".format(movieOutline.rating ?: "-")
        }


    }
}