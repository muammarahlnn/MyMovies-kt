package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.databinding.ItemRvVideosBinding
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
        private val binding = ItemRvVideosBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                clickListener.onVideoClicked(videoList[absoluteAdapterPosition])
            }
        }

        fun onBind(video: Video) {
            with (binding) {
                if (video.key != null || video.key == "") {
                    Utils.setImageGlide(
                        itemView.context,
                        video.getImageUrl(),
                        ivImageItemVideos)
                } else {
                    ivImageItemVideos.setBackgroundResource(R.drawable.img_placeholder)
                }
                tvTitleItemVideos.text = video.name ?: ""
            }
        }

    }
}