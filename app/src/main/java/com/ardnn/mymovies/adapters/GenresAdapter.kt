package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.listeners.FilmDetailClickListener
import com.ardnn.mymovies.models.Genre

class GenresAdapter(
    private val genreList: List<Genre>,
    private val clickListener: FilmDetailClickListener
): RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_rect, parent, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(genreList[position])
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    inner class ViewHolder(itemView: View, clickListener: FilmDetailClickListener) : RecyclerView.ViewHolder(itemView) {
        private val tvGenre: TextView

        init {
            itemView.setOnClickListener {
                clickListener.onGenreClicked(genreList[absoluteAdapterPosition])
            }
            tvGenre = itemView.findViewById(R.id.tv_name)
        }

        fun onBind(genre: Genre) {
            tvGenre.text = genre.name
        }
    }
}