package com.example.uts_pemogramanmobile2.ui.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uts_pemogramanmobile2.R
import com.example.uts_pemogramanmobile2.databinding.ItemProductBinding
import com.example.uts_pemogramanmobile2.model.Product
import java.util.Locale

class ProductAdapter(
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val products = mutableListOf<Product>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<Product>) {
        products.clear()
        products.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.tvTitle.text = product.title
            binding.tvCategory.text = product.category
            binding.tvPrice.text = String.format(Locale.US, "$%.2f", product.price)
            binding.tvStock.text = String.format(Locale.US, "• Stok: %d", product.stock)
            binding.tvRating.text = String.format(Locale.US, "%.2f", product.rating)

            Glide.with(itemView.context)
                .load(product.thumbnail)
                .placeholder(R.drawable.ic_products)
                .error(R.drawable.ic_products)
                .into(binding.imgThumbnail)

            itemView.setOnClickListener {
                onItemClick(product)
            }
        }
    }
}