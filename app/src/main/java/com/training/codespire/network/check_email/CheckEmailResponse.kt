package com.training.codespire.network.check_email


import com.google.gson.annotations.SerializedName

data class CheckEmailResponse(
    @SerializedName("exists")
    val exists: Boolean,
    @SerializedName("message")
    val message: String
)