package com.training.codespire.ui.frags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.training.codespire.R
import com.training.codespire.data.SearchResultAdapter
import com.training.codespire.data.viewmodel.AuthViewmodel
import com.training.codespire.databinding.FragmentSearchBinding
import com.training.codespire.ui.frags.categories.ProductsFragmentDirections

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var authViewModel: AuthViewmodel
    private lateinit var searchResultAdapter: SearchResultAdapter

    private var filterType: String = "Name" // Default filter type

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(this)[AuthViewmodel::class.java]

        searchResultAdapter = SearchResultAdapter(listOf()) { productId ->
            navigateToProductDetails(productId)
        }
        binding.searchRecyclerView.adapter = searchResultAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    authViewModel.searchProducts(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        authViewModel.searchResultsLiveAllProductsData.observe(viewLifecycleOwner) { allProducts ->
            filterProducts(binding.searchView.query.toString())
        }

        binding.icProductFilter.setOnClickListener {
            showFilterDialog()
        }

        return binding.root
    }

    private fun showFilterDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_filter, null)
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radio_group_filter)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Select Filter")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                filterType = when (radioGroup.checkedRadioButtonId) {
                    R.id.radio_name -> "Name"
                    R.id.radio_price -> "Price"
                    else -> "Name"
                }
                // Apply filter based on the selected filter type
                filterProducts(binding.searchView.query.toString())
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun filterProducts(query: String?) {
        val allProducts = authViewModel.searchResultsLiveAllProductsData.value ?: listOf()

        val filteredList = when (filterType) {
            "Name" -> {
                // Filter by name and sort alphabetically by name
                allProducts.filter { it.name.contains(query ?: "", ignoreCase = true) }
                    .sortedBy { it.name }
            }

            "Price" -> {
                // Filter by name if needed and sort by lowest price
                allProducts.filter { it.name.contains(query ?: "", ignoreCase = true) }
                    .sortedBy { it.price.toDoubleOrNull() ?: Double.MAX_VALUE }
            }

            else -> {
                // Default case: return all products without filtering or sorting
                allProducts
            }
        }

        // Log the filtered products for debugging
        Log.e("SearchFragment", "Filtered Products: $filteredList")

        searchResultAdapter.updateProducts(filteredList)
    }

    private fun navigateToProductDetails(productId: Int) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToProductDetailsFragment(productId)
        findNavController().navigate(action)
    }



}
