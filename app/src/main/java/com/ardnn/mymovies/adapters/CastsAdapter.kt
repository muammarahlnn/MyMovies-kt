package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.databinding.ItemRvCastBinding
import com.ardnn.mymovies.helpers.Utils
import com.ardnn.mymovies.listeners.FilmDetailClickListener
import com.ardnn.mymovies.models.Cast
import com.ardnn.mymovies.models.ImageSize

class CastsAdapter(
    private val castList: List<Cast>,
    private val clickListener: FilmDetailClickListener
) : RecyclerView.Adapter<CastsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_cast, parent, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(castList[position])
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    inner class ViewHolder(itemView: View, clickListener: FilmDetailClickListener)
        : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvCastBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                clickListener.onCastClicked(castList[absoluteAdapterPosition])
            }
        }

        fun onBind(cast: Cast) {
            with (binding) {
                if (cast.imageUrl != null || cast.imageUrl == "") {
                    Utils.setImageGlide(
                        itemView.context,
                        cast.getImageUrl(ImageSize.W342),
                        ivImageItemCast)
                } else {
                    ivImageItemCast.setImageResource(R.drawable.img_placeholder)
                }
                tvNameItemCast.text = cast.name ?: "-"
                tvCharacterItemCast.text = cast.character ?: "-"
            }
        }
    }
}