package com.training.codespire.network.product_details.review


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("comment")
    val comment: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)