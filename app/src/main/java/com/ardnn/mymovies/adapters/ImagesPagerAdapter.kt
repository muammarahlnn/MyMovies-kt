package com.ardnn.mymovies.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.models.Image
import com.ardnn.mymovies.models.ImageSize
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition

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

        return ViewHolder(view, onImageClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ViewHolder(itemView: View, onImageClick: OnImageClick)
        : RecyclerView.ViewHolder(itemView), RequestListener<Drawable> {
        private val ivImage: ImageView = itemView.findViewById(R.id.iv_item_images)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.pb_item_images)

        init {
            itemView.setOnClickListener {
                onImageClick.imageClicked()
            }
        }

        fun onBind(image: Image) {
            Glide.with(itemView.context)
                .load(image.getImageUrl(ImageSize.ORI))
                .listener(this)
                .into(ivImage)
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            if (resource != null) {
                target?.onResourceReady(
                    resource,
                    DrawableCrossFadeTransition(500, isFirstResource)
                )
            }
            progressBar.visibility = View.GONE
            return true
        }
    }

    interface OnImageClick {
        fun imageClicked()
    }

}