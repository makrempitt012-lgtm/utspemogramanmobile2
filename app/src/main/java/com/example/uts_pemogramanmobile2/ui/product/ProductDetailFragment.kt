package com.example.uts_pemogramanmobile2.ui.product

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.uts_pemogramanmobile2.R
import com.example.uts_pemogramanmobile2.databinding.FragmentProductDetailBinding
import com.example.uts_pemogramanmobile2.databinding.ItemReviewBinding
import com.example.uts_pemogramanmobile2.model.Product
import java.util.Locale

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("product", Product::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("product")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentProduct = product ?: return

        bindProductDetails(currentProduct)
        setupImagesGallery(currentProduct)
        setupTopReview(currentProduct)

        binding.btnViewAllReviews.setOnClickListener {
            val bottomSheet = ReviewsBottomSheetFragment.newInstance(
                currentProduct.title,
                currentProduct.reviews
            )
            bottomSheet.show(childFragmentManager, ReviewsBottomSheetFragment.TAG)
        }
    }

    private fun bindProductDetails(p: Product) {
        binding.tvDetailId.text = String.format(Locale.US, "ID Produk: #%d", p.id)
        binding.tvDetailTitle.text = p.title
        binding.tvDetailPrice.text = String.format(Locale.US, "$%.2f", p.price)
        binding.tvDetailCategory.text = p.category
        binding.tvDetailAvailability.text = p.availabilityStatus ?: "Tersedia"
        binding.tvDetailStock.text = String.format(Locale.US, "Stok: %d", p.stock)
        binding.tvDetailRating.text = String.format(Locale.US, "%.2f", p.rating)
        binding.tvDetailDescription.text = p.description

        // Dimensions
        val dim = p.dimensions
        if (dim != null) {
            binding.tvDetailDimensions.text = String.format(
                Locale.US,
                "Lebar: %.2f cm | Tinggi: %.2f cm | Kedalaman: %.2f cm",
                dim.width ?: 0.0,
                dim.height ?: 0.0,
                dim.depth ?: 0.0
            )
        } else {
            binding.tvDetailDimensions.text = getString(R.string.dim_not_available)
        }

        // Tags
        if (!p.tags.isNullOrEmpty()) {
            binding.tvDetailTags.text = p.tags.joinToString(separator = ", ") { "#$it" }
        } else {
            binding.tvDetailTags.text = "-"
        }

        // Main Thumbnail Image
        val mainImageUrl = p.thumbnail.ifEmpty {
            p.images?.firstOrNull() ?: ""
        }

        Glide.with(this)
            .load(mainImageUrl)
            .placeholder(R.drawable.ic_products)
            .error(R.drawable.ic_products)
            .into(binding.imgDetailMain)
    }

    private fun setupImagesGallery(p: Product) {
        val imageList = p.images
        if (!imageList.isNullOrEmpty()) {
            binding.tvGalleryTitle.visibility = View.VISIBLE
            binding.rvImages.visibility = View.VISIBLE
            val imageAdapter = ImageAdapter(imageList) { selectedUrl ->
                Glide.with(this)
                    .load(selectedUrl)
                    .placeholder(R.drawable.ic_products)
                    .error(R.drawable.ic_products)
                    .into(binding.imgDetailMain)
            }
            binding.rvImages.adapter = imageAdapter
        } else {
            binding.tvGalleryTitle.visibility = View.GONE
            binding.rvImages.visibility = View.GONE
        }
    }

    private fun setupTopReview(p: Product) {
        val reviews = p.reviews
        binding.containerTopReview.removeAllViews()

        if (!reviews.isNullOrEmpty()) {
            val topReview = reviews.first()
            val reviewBinding = ItemReviewBinding.inflate(
                layoutInflater,
                binding.containerTopReview,
                false
            )

            reviewBinding.tvReviewerName.text = topReview.reviewerName
            reviewBinding.tvReviewerEmail.text = topReview.reviewerEmail
            reviewBinding.tvReviewDate.text = topReview.date
            reviewBinding.tvReviewRating.text = String.format(Locale.US, "%d/5", topReview.rating ?: 0)
            reviewBinding.tvComment.text = topReview.comment

            binding.containerTopReview.addView(reviewBinding.root)
            binding.btnViewAllReviews.visibility = View.VISIBLE
            binding.btnViewAllReviews.text = String.format(
                Locale.US,
                "Lihat Semua Review (%d)",
                reviews.size
            )
        } else {
            binding.btnViewAllReviews.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}