package com.training.codespire.network

data class RegisterResponse(
    val message: String,
    val token: String,
    val user: User
)