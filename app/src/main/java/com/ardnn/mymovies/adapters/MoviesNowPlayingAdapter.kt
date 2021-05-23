package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.helpers.Const
import com.ardnn.mymovies.models.MoviesNowPlaying
import com.bumptech.glide.Glide

class MoviesNowPlayingAdapter(movieList: List<MoviesNowPlaying>, onItemClick: OnItemClick)
    : RecyclerView.Adapter<MoviesNowPlayingAdapter.ViewHolder>() {

    // attributes
    private var movieList: List<MoviesNowPlaying>
    private var onItemClick: OnItemClick

    init {
        this.movieList = movieList
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_films, parent, false)

        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // set data from MoviesNowPlaying to widgets on item_rv_films
        Glide.with(holder.itemView.context)
            .load("${Const.IMG_URL_300}${movieList[position].posterUrl}")
            .into(holder.ivPoster)
        holder.tvTitle.text = movieList[position].title
        holder.tvTitle.text = movieList[position].releaseDate.substring(0, 4)
        holder.tvTitle.text = movieList[position].vote.toString()
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class ViewHolder(itemView: View, onItemClick: OnItemClick) : RecyclerView.ViewHolder(itemView) {
        val onItemClick: OnItemClick
        val ivPoster: ImageView
        val tvTitle: TextView
        val tvYear: TextView
        val tvVote: TextView

        init {
            this.onItemClick = onItemClick
            itemView.setOnClickListener {
                onItemClick.onClick(absoluteAdapterPosition)
            }

            ivPoster = itemView.findViewById(R.id.iv_poster_item_films)
            tvTitle = itemView.findViewById(R.id.tv_title_item_films)
            tvYear = itemView.findViewById(R.id.tv_year_item_films)
            tvVote = itemView.findViewById(R.id.tv_vote_item_films)
        }
    }

    interface OnItemClick {
        fun onClick(position: Int)
    }

}