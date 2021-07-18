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
import com.ardnn.mymovies.listeners.SingleClickListener
import com.ardnn.mymovies.models.ImageSize
import com.ardnn.mymovies.models.MovieOutline

class MoviesPrimaryAdapter(
    private val movieList: MutableList<MovieOutline>,
    private val clickListener: SingleClickListener<MovieOutline>
) : RecyclerView.Adapter<MoviesPrimaryAdapter.ViewHolder>(), Filterable {

    // list to save all fetched movies
    private val listFull: MutableList<MovieOutline> = mutableListOf()

    init {
        listFull.addAll(movieList)
    }

    fun updateList(updatedList: MutableList<MovieOutline>) {
        listFull.clear()
        listFull.addAll(updatedList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_films_primary, parent, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class ViewHolder(itemView: View, clickListener: SingleClickListener<MovieOutline>) : RecyclerView.ViewHolder(itemView) {
        private val ivPoster: ImageView
        private val tvTitle: TextView
        private val tvYear: TextView
        private val tvVote: TextView

        init {
            itemView.setOnClickListener {
                clickListener.onItemClicked(movieList[absoluteAdapterPosition])
            }
            ivPoster = itemView.findViewById(R.id.iv_poster_item_films_primary)
            tvTitle = itemView.findViewById(R.id.tv_title_item_films_primary)
            tvYear = itemView.findViewById(R.id.tv_year_item_films_primary)
            tvVote = itemView.findViewById(R.id.tv_vote_item_films_primary)
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
            tvVote.text = movieOutline.rating?.toString() ?: "-"
        }
    }

    // search movies
    private var isSearching: Boolean = false
    fun getIsSearching() : Boolean = isSearching
    fun setIsSearching(isSearching: Boolean) {
        this.isSearching = isSearching
    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    private val searchFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<MovieOutline> = mutableListOf()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(listFull)
                isSearching = false
            } else {
                val filterPattern: String = constraint.toString().trim()
                for (movie in listFull) {
                    val title: String = movie.title ?: "-"
                    val year: String =
                        if (movie.releaseDate == null || movie.releaseDate == "") "-"
                        else movie.releaseDate.substring(0, 4)

                    if (title.startsWith(filterPattern, true) || year.startsWith(filterPattern, true)) {
                        filteredList.add(movie)
                    }
                }
            }

            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            movieList.clear()
            movieList.addAll(results?.values as MutableList<MovieOutline>)
            notifyDataSetChanged()
        }

    }
}