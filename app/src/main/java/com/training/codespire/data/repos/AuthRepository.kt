package com.training.codespire.data.repos

import android.content.Context
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.network.ApiService
import com.training.codespire.network.RetrofitClient
import com.training.codespire.network.all_products.AllProductsResponse
import com.training.codespire.network.auth.LoginRequest
import com.training.codespire.network.auth.LoginResponse
import com.training.codespire.network.auth.RegisterRequest
import com.training.codespire.network.auth.RegisterResponse
import com.training.codespire.network.my_orders.MyOrdersResponse
import com.training.codespire.network.payment.PaymentRequest
import com.training.codespire.network.payment.PaymentResponse
import com.training.codespire.network.product_details.ProductDetailsResponse
import com.training.codespire.network.product_details.review.ReviewRequest
import com.training.codespire.network.product_details.review.ReviewResponse
import com.training.codespire.network.products.CategoryResponse
import retrofit2.Response

class AuthRepository(private val context: Context) {


    private val api: ApiService = RetrofitClient.apiService
    private val sharedPreferencesUtil = SharedPreferencesUtil(context)


    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return api.registerUser(registerRequest)
    }

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        val response = api.loginUser(loginRequest)
        if (response.isSuccessful) {
            response.body()?.let { loginResponse ->
                sharedPreferencesUtil.token = loginResponse.token
                sharedPreferencesUtil.username = loginResponse.user.name
                sharedPreferencesUtil.isLoggedIn = true
            }
        }
        return response
    }

    suspend fun logoutUser(): Response<Void> {
        val token = sharedPreferencesUtil.token
        val response = api.logoutUser("Bearer $token")
        if (response.isSuccessful) {
            sharedPreferencesUtil.clear()
        }
        return response
    }

    suspend fun getProductsByCategory(): Response<CategoryResponse> {
        val token = sharedPreferencesUtil.token
        val categoryId = sharedPreferencesUtil.categoryId
        return api.getProductsByCategory("Bearer $token", categoryId)

    }


    suspend fun getAllProducts(): Response<AllProductsResponse> {
        val token = sharedPreferencesUtil.token
        return api.getAllProducts("Bearer $token")
    }


    suspend fun getProductDetails(id: Int): Response<ProductDetailsResponse> {
        val token = sharedPreferencesUtil.token
        return api.getProductDetails("Bearer $token", id)
    }

    suspend fun submitReview(
        productId: Int,
        reviewRequest: ReviewRequest
    ): Response<ReviewResponse> {
        val token = sharedPreferencesUtil.token
        return api.submitReview(productId, "Bearer $token", reviewRequest)
    }


    suspend fun makePayment(
        productId: Int, paymentRequest: PaymentRequest
    ): Response<PaymentResponse> {
        val token = sharedPreferencesUtil.token
        return api.makePayment(productId, paymentRequest, "Bearer $token")
    }

    suspend fun getOrders(): Response<MyOrdersResponse> {
        val token = sharedPreferencesUtil.token
        return api.getOrders("Bearer $token")
    }


}