package com.training.codespire.network.auth

data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserX
)