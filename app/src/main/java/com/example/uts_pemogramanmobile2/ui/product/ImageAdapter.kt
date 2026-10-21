package com.example.uts_pemogramanmobile2.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uts_pemogramanmobile2.R
import com.example.uts_pemogramanmobile2.databinding.ItemProductImageBinding

class ImageAdapter(
    private val images: List<String>,
    private val onImageClick: ((String) -> Unit)? = null
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemProductImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    inner class ImageViewHolder(
        private val binding: ItemProductImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_products)
                .error(R.drawable.ic_products)
                .into(binding.imgProduct)

            itemView.setOnClickListener {
                onImageClick?.invoke(imageUrl)
            }
        }
    }
}