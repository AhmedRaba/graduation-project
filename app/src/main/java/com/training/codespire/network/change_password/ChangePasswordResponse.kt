package com.training.codespire.network.change_password


import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    @SerializedName("message")
    val message: String
)