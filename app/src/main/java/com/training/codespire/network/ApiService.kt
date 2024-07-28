package com.training.codespire.network

import com.training.codespire.network.all_products.AllProductsResponse
import com.training.codespire.network.auth.LoginRequest
import com.training.codespire.network.auth.LoginResponse
import com.training.codespire.network.auth.RegisterRequest
import com.training.codespire.network.auth.RegisterResponse
import com.training.codespire.network.payment.PaymentRequest
import com.training.codespire.network.payment.PaymentResponse
import com.training.codespire.network.product_details.ProductDetailsResponse
import com.training.codespire.network.product_details.review.ReviewRequest
import com.training.codespire.network.product_details.review.ReviewResponse
import com.training.codespire.network.products.CategoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>


    @POST("login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("logout")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<Void>

    @GET("categories/{id}/products")
    suspend fun getProductsByCategory(
        @Header("Authorization") token: String,
        @Path("id") categoryId: Int
    ): Response<CategoryResponse>

    @GET("products")
    suspend fun getAllProducts(@Header("Authorization") token: String): Response<AllProductsResponse>

    @GET("products/{id}")
    suspend fun getProductDetails(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<ProductDetailsResponse>

    @POST("products/{id}/reviews")
    suspend fun submitReview(
        @Path("id") productId: Int,
        @Header("Authorization") token: String,
        @Body reviewRequest: ReviewRequest
    ): Response<ReviewResponse>


    @POST("payment/{id}")
    suspend fun makePayment(
        @Path("id") productId: Int,
        @Body paymentRequest: PaymentRequest,
        @Header("Authorization") token: String
    ): Response<PaymentResponse>


}