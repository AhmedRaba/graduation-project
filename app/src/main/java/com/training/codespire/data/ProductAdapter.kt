package com.training.codespire.data

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.training.codespire.R
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

            // Show Lottie animation
            loadingAnimation.visibility = View.VISIBLE
            loadingAnimation.playAnimation()

            // Set a placeholder image for ImageView while the real image is loading
            ivProduct.setImageResource(R.drawable.iv_dev_store_logo) // Use a placeholder drawable

            imageUrl?.let {
                Glide.with(root.context)
                    .load(it)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.iv_dev_store_logo) // Use a placeholder drawable
                    .into(object : CustomTarget<android.graphics.drawable.Drawable>() {
                        override fun onResourceReady(resource: android.graphics.drawable.Drawable, transition: Transition<in android.graphics.drawable.Drawable>?) {
                            // Hide the Lottie animation and set the image
                            loadingAnimation.visibility = View.GONE
                            loadingAnimation.cancelAnimation()
                            ivProduct.setImageDrawable(resource)
                        }

                        override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {
                            // Handle cleanup if necessary
                        }
                    })
            } ?: run {
                // Hide the Lottie animation if no image URL is present
                loadingAnimation.visibility = View.GONE
                loadingAnimation.cancelAnimation()
            }
        }
    }
}
