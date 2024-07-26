package com.training.codespire.network.payment

data class PaymentRequest(
    val card_number:String,
    val expiry_date:String,
    val cvv:String
)
