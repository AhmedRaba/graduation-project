package com.training.codespire.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.training.codespire.R
import com.training.codespire.databinding.ItemProductBinding
import com.training.codespire.network.all_products.AllProductsData

class SearchResultAdapter(private val allProducts: List<AllProductsData>) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    inner class SearchResultViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return allProducts.size
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val product = allProducts[position]
        val imageUrl = product.images.firstOrNull()

        with(holder.binding) {
            tvProductPrice.text = "$" + product.price
            tvProductName.text = product.name

            // Make sure the Lottie animation view is visible
            loadingAnimation.visibility = View.VISIBLE
            loadingAnimation.playAnimation()

            ivProduct.setImageResource(R.drawable.white_background)

            imageUrl?.let {
                Glide.with(root.context)
                    .load(it)
                    .placeholder(R.drawable.iv_dev_store_logo) // Replace with your placeholder image
                    .error(R.drawable.iv_dev_store_logo) // Replace with your error image
                    .into(object : CustomTarget<android.graphics.drawable.Drawable>() {
                        override fun onResourceReady(resource: android.graphics.drawable.Drawable, transition: Transition<in android.graphics.drawable.Drawable>?) {
                            // Set the image and hide the animation
                            ivProduct.setImageDrawable(resource)
                            loadingAnimation.visibility = View.GONE
                            loadingAnimation.cancelAnimation()
                        }

                        override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {
                            // Handle cleanup if necessary
                        }
                    })
            } ?: run {
                // If no imageUrl is present, hide the animation
                loadingAnimation.visibility = View.GONE
                loadingAnimation.cancelAnimation()
            }
        }
    }
}
