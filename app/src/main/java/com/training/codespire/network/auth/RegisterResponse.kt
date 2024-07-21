package com.training.codespire.network.auth

data class RegisterResponse(
    val message: String,
    val token: String,
    val user: User
)