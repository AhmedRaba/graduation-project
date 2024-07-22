package com.training.codespire.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.codespire.databinding.ItemProductBinding
import com.training.codespire.network.all_products.AllProductsData

class SearchResultAdapter(private val allProducts: List<AllProductsData>) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {


    inner class SearchResultViewHolder(val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding=ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchResultViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return allProducts.size
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val product=allProducts[position]

        val imageUrl=product.images.firstOrNull()

        with(holder.binding){


            tvProductPrice.text = "$" + product.price
            tvProductName.text = product.name

            imageUrl?.let {
                Glide.with(root.context)
                    .load(it)
                    .into(ivProduct)
            }

        }


    }

}