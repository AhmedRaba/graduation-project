package com.training.codespire.ui.frags.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.training.codespire.R
import com.training.codespire.data.ProductAdapter
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.data.viewmodel.AuthViewmodel
import com.training.codespire.databinding.FragmentProductsBinding
import com.training.codespire.network.products.ProductData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    private lateinit var authViewModel: AuthViewmodel
    private lateinit var productAdapter: ProductAdapter

    private var filterType: String? = null // No default filter type

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        sharedPreferencesUtil = SharedPreferencesUtil(requireContext())
        authViewModel = ViewModelProvider(this)[AuthViewmodel::class.java]

        val categoryId = sharedPreferencesUtil.categoryId
        setCategoryName(categoryId)

        productAdapter = ProductAdapter(emptyList()) { productId ->
            navigateToProductDetails(productId)
        }
        binding.recyclerView.adapter = productAdapter

        showLoading()

        authViewModel.getProductsByCategory()
        authViewModel.productsByCategoryResponseLiveData.observe(
            viewLifecycleOwner
        ) { products ->
            lifecycleScope.launch {
                delay(2000) // Simulate delay for loading
                updateProductList(products)
                hideLoading()
            }
        }

        binding.icProductFilter.setOnClickListener {
            showFilterDialog()
        }

        return binding.root
    }

    private fun updateProductList(products: List<ProductData>) {
        val filteredProducts = when (filterType) {
            "Price" -> products.sortedBy { it.price.toDoubleOrNull() ?: Double.MAX_VALUE }
            "Name" -> products.sortedBy { it.name }
            else -> products // No filtering
        }

        productAdapter = ProductAdapter(filteredProducts) { productId ->
            navigateToProductDetails(productId)
        }
        binding.recyclerView.adapter = productAdapter
    }

    private fun showFilterDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_filter, null)
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radio_group_filter)
        val radioPrice = dialogView.findViewById<RadioButton>(R.id.radio_price)

        if (sharedPreferencesUtil.categoryId==13) {
            radioPrice.visibility = View.GONE
        }else{
            radioPrice.visibility=View.VISIBLE
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Select Filter")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                filterType = when (radioGroup.checkedRadioButtonId) {
                    R.id.radio_name -> "Name"
                    R.id.radio_price -> "Price"
                    else -> null // No filter
                }
                authViewModel.getProductsByCategory()
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun navigateToProductDetails(productId: Int) {
        val action =
            ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(productId)
        findNavController().navigate(action)
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

    override fun onDestroyView() {
        super.onDestroyView()
        // No need to reset filterType here
    }
}

