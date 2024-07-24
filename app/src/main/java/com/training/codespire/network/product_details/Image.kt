package com.training.codespire.network.product_details


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("image_name")
    val imageName: String,
    @SerializedName("url")
    val url: String
)