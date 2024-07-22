package com.training.codespire.ui.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.training.codespire.data.SearchResultAdapter
import com.training.codespire.data.viewmodel.AuthViewmodel
import com.training.codespire.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var authViewModel: AuthViewmodel
    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(this)[AuthViewmodel::class.java]



        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
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
            searchResultAdapter = SearchResultAdapter(allProducts)
            binding.searchRecyclerView.adapter = searchResultAdapter
        }





        return binding.root
    }

}