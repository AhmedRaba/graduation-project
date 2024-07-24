package com.training.codespire.network.product_details


import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("can_review")
    val canReview: Boolean,
    @SerializedName("category")
    val category: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("reviews")
    val reviews: List<Review>,
    @SerializedName("reviews_count")
    val reviewsCount: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("download_url")
    val downloadUrl: String
)
