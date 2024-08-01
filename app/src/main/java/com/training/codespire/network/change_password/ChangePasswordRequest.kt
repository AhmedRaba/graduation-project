package com.training.codespire.network.change_password

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(

    @SerializedName("new_password")
    val newPassword: String,
    @SerializedName("new_password_confirmation")
    val newPasswordConfirmation: String
)
