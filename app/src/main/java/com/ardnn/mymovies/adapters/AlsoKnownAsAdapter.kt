package com.ardnn.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.mymovies.R
import com.ardnn.mymovies.databinding.ItemRvRectBinding

class AlsoKnownAsAdapter(
    private val akaList: List<String>
): RecyclerView.Adapter<AlsoKnownAsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_rect, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(akaList[position])
    }

    override fun getItemCount(): Int {
        return akaList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvRectBinding.bind(itemView)

        fun bind(aka: String) {
            with(binding) {
                tvName.text = aka
            }
        }
    }

}