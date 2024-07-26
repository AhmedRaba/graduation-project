package com.training.codespire.network.product_details


import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("uploaded_by")
    val uploadedBy: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("category")
    val category: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("reviews")
    val reviews: List<Review>,
    @SerializedName("reviews_count")
    val reviewsCount: Int,
    @SerializedName("can_review")
    val canReview: Boolean,
    @SerializedName("can_buy")
    val canBuy: Boolean,
    @SerializedName("download_url")
    val downloadUrl: String,
    @SerializedName("payment_status")
    val paymentStatus: Boolean
)
