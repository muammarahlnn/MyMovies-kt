package com.ardnn.mymovies.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.databinding.ItemRvImagesBinding
import com.ardnn.mymovies.listeners.SingleClickListener
import com.ardnn.mymovies.models.Image
import com.ardnn.mymovies.models.ImageSize
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition

class ImagesPagerAdapter(
    private val imageList: List<Image>,
    private val clickListener: SingleClickListener<Image>
): RecyclerView.Adapter<ImagesPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_images, parent, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ViewHolder(itemView: View, clickListener: SingleClickListener<Image>)
        : RecyclerView.ViewHolder(itemView), RequestListener<Drawable> {
        private val binding = ItemRvImagesBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClicked(imageList[absoluteAdapterPosition])
            }
        }

        fun onBind(image: Image) {
            Glide.with(itemView.context)
                .load(image.getImageUrl(ImageSize.ORI))
                .listener(this)
                .into(binding.ivItemImages)
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
            binding.pbItemImages.visibility = View.GONE
            return true
        }
    }

}