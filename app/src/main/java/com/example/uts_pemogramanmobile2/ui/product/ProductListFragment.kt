package com.example.uts_pemogramanmobile2.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.uts_pemogramanmobile2.R
import com.example.uts_pemogramanmobile2.api.RetrofitClient
import com.example.uts_pemogramanmobile2.databinding.FragmentProductListBinding
import com.example.uts_pemogramanmobile2.model.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchProducts()

        binding.btnRetry.setOnClickListener {
            fetchProducts()
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter { product ->
            val bundle = Bundle().apply {
                putParcelable("product", product)
            }
            findNavController().navigate(
                R.id.action_productListFragment_to_productDetailFragment,
                bundle
            )
        }
        binding.rvProducts.adapter = productAdapter
    }

    private fun fetchProducts() {
        showLoading(isLoading = true)
        showError(isError = false)

        RetrofitClient.instance.getProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                showLoading(isLoading = false)
                val body = response.body()
                if (response.isSuccessful && (body != null)) {
                    val productsList = body.products
                    if (productsList.isNotEmpty()) {
                        productAdapter.submitList(productsList)
                    } else {
                        showError(isError = true, message = "Data produk kosong.")
                    }
                } else {
                    showError(isError = true, message = "Terjadi kesalahan server (${response.code()}).")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                showLoading(isLoading = false)
                showError(isError = true, message = t.localizedMessage ?: getString(R.string.error_fetch))
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            binding.rvProducts.visibility = View.GONE
        } else {
            binding.rvProducts.visibility = View.VISIBLE
        }
    }

    private fun showError(isError: Boolean, message: String? = null) {
        binding.layoutError.visibility = if (isError) View.VISIBLE else View.GONE
        if (isError) {
            binding.rvProducts.visibility = View.GONE
            binding.tvErrorMessage.text = message ?: getString(R.string.error_fetch)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}