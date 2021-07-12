package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.models.Image
import com.ardnn.mymovies.models.ImageSize
import com.bumptech.glide.Glide

class ImagesPagerAdapter: RecyclerView.Adapter<ImagesPagerAdapter.ViewHolder>() {
    private lateinit var imageList: List<Image>
    private lateinit var onImageClick: OnImageClick

    fun setImageList(imageList: List<Image>) {
        this.imageList = imageList
    }

    fun setOnImageClick(onImageClick: OnImageClick) {
        this.onImageClick = onImageClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_images, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(imageList[position].getImageUrl(ImageSize.ORI))
            .into(holder.ivImage)

        // if clicked
        holder.itemView.setOnClickListener {
            onImageClick.imageClicked()
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.iv_item_images)
    }

    interface OnImageClick {
        fun imageClicked()
    }

}