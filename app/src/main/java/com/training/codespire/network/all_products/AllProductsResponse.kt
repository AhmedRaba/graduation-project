package com.training.codespire.network.all_products


import com.google.gson.annotations.SerializedName

data class AllProductsResponse(
    @SerializedName("data")
    val data: List<AllProductsData>
)