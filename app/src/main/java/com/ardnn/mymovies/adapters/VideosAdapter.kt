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
import com.ardnn.mymovies.models.Video

class VideosAdapter(
    private val videoList: List<Video>,
    private val clickListener: FilmDetailClickListener
) : RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_videos, parent, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(videoList[position])
    }

    override fun getItemCount(): Int {
        return videoList.size
    }


    inner class ViewHolder(itemView: View, clickListener: FilmDetailClickListener)
        : RecyclerView.ViewHolder(itemView) {
        private val ivImage: ImageView = itemView.findViewById(R.id.iv_image_item_videos)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title_item_videos)

        init {
            itemView.setOnClickListener {
                clickListener.onVideoClicked(videoList[absoluteAdapterPosition])
            }
        }

        fun onBind(video: Video) {
            if (video.key != null || video.key == "") {
                Utils.setImageGlide(
                    itemView.context,
                    video.getImageUrl(),
                    ivImage)
            } else {
                ivImage.setBackgroundResource(R.drawable.img_placeholder)
            }
            tvTitle.text = video.name ?: ""
        }

    }
}