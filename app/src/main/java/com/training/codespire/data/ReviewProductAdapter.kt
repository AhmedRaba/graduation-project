package com.training.codespire.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.codespire.databinding.ItemProductReviewBinding
import com.training.codespire.network.product_details.Review

class ReviewProductAdapter(private val reviews: List<Review>):RecyclerView.Adapter<ReviewProductAdapter.ReviewProductViewHolder>() {


    inner class ReviewProductViewHolder(val binding: ItemProductReviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewProductViewHolder {
        val binding = ItemProductReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewProductViewHolder, position: Int) {

        val review=reviews[position]

        with(holder.binding){

            tvReviewUserName.text=review.user
            tvReviewComment.text=review.comment
            ratingBarReview.rating=review.rating.toFloat()

        }
    }


}

