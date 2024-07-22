package com.training.codespire.network.products


import com.google.gson.annotations.SerializedName

data class ProductData(
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String
)