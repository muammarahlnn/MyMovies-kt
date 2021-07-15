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

    inner class ViewHolder(itemView: View, clickListener: FilmDetailClickListener) : RecyclerView.ViewHolder(itemView) {
        private val ivImage: ImageView
        private val tvName: TextView
        private val tvCharacter: TextView

        init {
            itemView.setOnClickListener {
                clickListener.onCastClicked(castList[absoluteAdapterPosition])
            }
            ivImage = itemView.findViewById(R.id.iv_image_item_cast)
            tvName = itemView.findViewById(R.id.tv_name_item_cast)
            tvCharacter = itemView.findViewById(R.id.tv_character_item_cast)
        }

        fun onBind(cast: Cast) {
            if (cast.imageUrl != null || cast.imageUrl == "") {
                Utils.setImageGlide(
                    itemView.context,
                    cast.getImageUrl(ImageSize.W342),
                    ivImage)
            } else {
                ivImage.setImageResource(R.drawable.img_placeholder)
            }
            tvName.text = cast.name ?: "-"
            tvCharacter.text = cast.character ?: "-"
        }
    }
}