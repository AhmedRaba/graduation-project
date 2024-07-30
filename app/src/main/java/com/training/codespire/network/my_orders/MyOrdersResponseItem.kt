package com.training.codespire.network.my_orders


import com.google.gson.annotations.SerializedName

data class MyOrdersResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("image")
    val image:String,
    @SerializedName("download_link")
    val downloadUrl:String

)