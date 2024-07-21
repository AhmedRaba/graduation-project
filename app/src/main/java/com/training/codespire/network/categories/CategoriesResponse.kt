package com.training.codespire.network.categories


import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("data")
    val data: List<Data>
)