package com.training.codespire.network.all_products


import com.google.gson.annotations.SerializedName

data class AllProductsData(
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