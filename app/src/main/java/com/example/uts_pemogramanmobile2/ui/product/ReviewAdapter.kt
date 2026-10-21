package com.example.uts_pemogramanmobile2.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_pemogramanmobile2.databinding.ItemReviewBinding
import com.example.uts_pemogramanmobile2.model.Review
import java.util.Locale

class ReviewAdapter(
    private val reviews: List<Review>
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int = reviews.size

    class ReviewViewHolder(
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.tvReviewerName.text = review.reviewerName
            binding.tvReviewerEmail.text = review.reviewerEmail
            binding.tvReviewDate.text = review.date
            binding.tvReviewRating.text = String.format(Locale.US, "%d/5", review.rating ?: 0)
            binding.tvComment.text = review.comment
        }
    }
}