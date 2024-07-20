package com.training.codespire.network

data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserX
)