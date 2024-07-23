package com.training.codespire.ui.frags.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.training.codespire.data.ProductAdapter
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.data.viewmodel.AuthViewmodel
import com.training.codespire.databinding.FragmentProductsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    private lateinit var authViewModel: AuthViewmodel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater)
        sharedPreferencesUtil = SharedPreferencesUtil(requireContext())
        authViewModel = ViewModelProvider(this)[AuthViewmodel::class.java]

        val categoryId = sharedPreferencesUtil.categoryId
        setCategoryName(categoryId)
        productAdapter = ProductAdapter(emptyList())
        binding.recyclerView.adapter = productAdapter

        showLoading()

        authViewModel.getProductsByCategory()
        authViewModel.productsByCategoryResponseLiveData.observe(viewLifecycleOwner, Observer { products ->
            lifecycleScope.launch {
                // Simulate a delay for loading images
                delay(2000)
                productAdapter = ProductAdapter(products)
                binding.recyclerView.adapter = productAdapter
                hideLoading()
            }
        })

        return binding.root
    }

    private fun setCategoryName(categoryId: Int) {
        when (categoryId) {
            1 -> binding.tvProductCategory.text = "Front End"
            2 -> binding.tvProductCategory.text = "Back End"
            3 -> binding.tvProductCategory.text = "AI"
            4 -> binding.tvProductCategory.text = "Mobile"
            5 -> binding.tvProductCategory.text = "Website"
            6 -> binding.tvProductCategory.text = "Desktop"
            13 -> binding.tvProductCategory.text = "Free"
        }
    }

    private fun showLoading() {
        binding.recyclerView.visibility = View.INVISIBLE
        binding.progressBarProducts.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBarProducts.visibility = View.GONE
    }
}
