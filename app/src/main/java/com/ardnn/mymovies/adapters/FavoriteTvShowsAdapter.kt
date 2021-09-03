package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.database.entities.FavoriteTvShows
import com.ardnn.mymovies.databinding.ItemRvFavoriteBinding
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.FavoriteClickListener
import com.ardnn.mymovies.listeners.SingleClickListener

class FavoriteTvShowsAdapter(
    private val favoriteTvShowList: List<FavoriteTvShows>,
    private val clickListener: FavoriteClickListener
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


    inner class ViewHolder(itemView: View, clickListener: FavoriteClickListener)
        : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvFavoriteBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClicked(favoriteTvShowList[absoluteAdapterPosition])
            }
        }

        fun onBind(favoriteTvShow: FavoriteTvShows) {
            with(binding) {
                if (favoriteTvShow.posterUrl != null || favoriteTvShow.posterUrl == "") {
                    Utils.setImageGlide(
                        itemView.context,
                        favoriteTvShow.posterUrl,
                        ivPosterItemFavorite)
                } else {
                    ivPosterItemFavorite.setImageResource(R.drawable.img_placeholder)
                }
                tvTitleItemFavorite.text = favoriteTvShow.title
                tvYearItemFavorite.text = favoriteTvShow.releaseDate.substring(0, 4)
                tvRatingItemFavorite.text = favoriteTvShow.rating.toString()
            }
        }
    }
}