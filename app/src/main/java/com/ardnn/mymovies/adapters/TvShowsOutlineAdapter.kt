package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.models.ImageSize
import com.ardnn.mymovies.models.TvShowOutline
import com.bumptech.glide.Glide

class TvShowsOutlineAdapter(
    private var tvShowList: List<TvShowOutline>,
    private var onItemClick: OnItemClick
) : RecyclerView.Adapter<TvShowsOutlineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_films, parent, false)

        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(tvShowList[position])
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    inner class ViewHolder(itemView: View, onItemClick: OnItemClick) : RecyclerView.ViewHolder(itemView) {
        private val ivPoster: ImageView
        private val tvTitle: TextView
        private val tvYear: TextView
        private val tvVote: TextView

        init {
            itemView.setOnClickListener {
                onItemClick.itemClicked(tvShowList[absoluteAdapterPosition])
            }
            ivPoster = itemView.findViewById(R.id.iv_poster_item_films)
            tvTitle = itemView.findViewById(R.id.tv_title_item_films)
            tvYear = itemView.findViewById(R.id.tv_year_item_films)
            tvVote = itemView.findViewById(R.id.tv_vote_item_films)
        }

        fun onBind(tvShowOutline: TvShowOutline) {
            // set data to widgets
            Glide.with(itemView.context)
                .load(tvShowOutline.getPosterUrl(ImageSize.W342))
                .into(ivPoster)
            tvTitle.text = tvShowOutline.title ?: "null"
            tvYear.text = tvShowOutline.releaseDate?.substring(0, 4) ?: "null"
            tvVote.text = tvShowOutline.rating?.toString() ?: "0.0"
        }
    }
}