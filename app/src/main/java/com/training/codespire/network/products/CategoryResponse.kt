package com.training.codespire.network.products


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("data")
    val data: List<ProductData>
)