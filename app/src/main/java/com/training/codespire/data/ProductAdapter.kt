package com.training.codespire.data

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.codespire.databinding.ItemProductBinding
import com.training.codespire.network.products.ProductData

class ProductAdapter(private val products: List<ProductData>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {


    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val product = products[position]


        val imageUrl = product.images.firstOrNull()

        Log.e("ProductAdapter", imageUrl.toString())

        with(holder.binding) {
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