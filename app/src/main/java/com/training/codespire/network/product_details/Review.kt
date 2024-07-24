package com.training.codespire.network.product_details

data class Review(
    val id: Int,
    val user: String,
    val rating: Int,
    val comment: String,
    val created_at: String
)
