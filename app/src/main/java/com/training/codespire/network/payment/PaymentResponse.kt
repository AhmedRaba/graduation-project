package com.training.codespire.network.payment


import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("download_url")
    val downloadUrl: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("product_name")
    val productName:String
)