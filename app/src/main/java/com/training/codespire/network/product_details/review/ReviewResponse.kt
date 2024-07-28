package com.training.codespire.network.product_details.review


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("review")
    val review: Review
)