package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.models.ImageSize
import com.ardnn.mymovies.models.TvShowOutline
import com.bumptech.glide.Glide

class TvShowsOutlineAdapter(
    private val tvShowList: MutableList<TvShowOutline>,
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<TvShowsOutlineAdapter.ViewHolder>(), Filterable {

    // list to save all fetched tv shows
    private val listFull: MutableList<TvShowOutline> = mutableListOf()

    init {
        listFull.addAll(tvShowList)
    }

    fun updateList(updatedList: MutableList<TvShowOutline>) {
        listFull.clear()
        listFull.addAll(updatedList)
        notifyDataSetChanged()
    }

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
            if (tvShowOutline.posterUrl != null || tvShowOutline.posterUrl == "") {
                Utils.setImageGlide(
                    itemView.context,
                    tvShowOutline.getPosterUrl(ImageSize.W342),
                    ivPoster)
            } else {
                ivPoster.setImageResource(R.drawable.img_placeholder)
            }
            tvTitle.text = tvShowOutline.title ?: "-"
            tvYear.text =
                if (tvShowOutline.releaseDate == null || tvShowOutline.releaseDate == "") "-"
                else tvShowOutline.releaseDate.substring(0, 4)
            tvVote.text = tvShowOutline.rating?.toString() ?: "-"
        }
    }

    // search tv shows
    private var isSearching: Boolean = false
    fun getIsSearching(): Boolean = isSearching
    fun setIsSearching(isSearching: Boolean) {
        this.isSearching = isSearching
    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    private val searchFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<TvShowOutline> = mutableListOf()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(listFull)
                isSearching = false
            } else {
                val filterPattern: String = constraint.toString().trim()
                for (tvShow in listFull) {
                    val title: String = tvShow.title ?: "-"
                    val year: String =
                        if (tvShow.releaseDate == null || tvShow.releaseDate == "") "-"
                        else tvShow.releaseDate.substring(0, 4)

                    if (title.startsWith(filterPattern, true) || year.startsWith(filterPattern, true)) {
                        filteredList.add(tvShow)
                    }
                }
            }

            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            tvShowList.clear()
            tvShowList.addAll(results?.values as MutableList<TvShowOutline>)
            notifyDataSetChanged()
        }

    }
}