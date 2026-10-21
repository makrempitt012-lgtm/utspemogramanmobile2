package com.example.uts_pemogramanmobile2.ui.product

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uts_pemogramanmobile2.databinding.FragmentReviewsBottomSheetBinding
import com.example.uts_pemogramanmobile2.model.Review
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReviewsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentReviewsBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productName = arguments?.getString(ARG_PRODUCT_NAME) ?: ""
        
        val reviews: List<Review> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList(ARG_REVIEWS, Review::class.java) ?: emptyList()
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelableArrayList<Review>(ARG_REVIEWS) ?: emptyList()
        }

        binding.tvProductName.text = productName

        if (reviews.isNotEmpty()) {
            binding.rvAllReviews.adapter = ReviewAdapter(reviews)
            binding.rvAllReviews.visibility = View.VISIBLE
            binding.tvNoReviews.visibility = View.GONE
        } else {
            binding.rvAllReviews.visibility = View.GONE
            binding.tvNoReviews.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ReviewsBottomSheetFragment"
        private const val ARG_PRODUCT_NAME = "arg_product_name"
        private const val ARG_REVIEWS = "arg_reviews"

        fun newInstance(productName: String, reviews: List<Review>?): ReviewsBottomSheetFragment {
            val fragment = ReviewsBottomSheetFragment()
            val args = Bundle().apply {
                putString(ARG_PRODUCT_NAME, productName)
                if (reviews != null) {
                    putParcelableArrayList(ARG_REVIEWS, ArrayList(reviews))
                }
            }
            fragment.arguments = args
            return fragment
        }
    }
}