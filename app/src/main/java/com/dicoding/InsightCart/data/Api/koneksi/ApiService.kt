package com.dicoding.InsightCart.data.Api.koneksi

import MenuResponseItem
import com.dicoding.InsightCart.data.Api.Response.CheckoutResponse
import com.dicoding.picodiploma.loginwithanimation.data.Api.Response.SignUpResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("signup")
    fun postSignUp(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SignUpResponse>

    @GET("getAllReceipts")
     fun getAllReceipts(): Call<List<AllTransactionItem>>


    @POST("checkout")
    fun checkout(@Body request: CheckoutRequest): Call<CheckoutResponse>

    @GET("print-receipt/{transactionId}")
    fun getReceipt(@Path("transactionId") transactionId: String): Call<ReceiptResponse>

    @GET("getMenuItems")
    fun getMenu(): Call<List<MenuResponseItem>>



}