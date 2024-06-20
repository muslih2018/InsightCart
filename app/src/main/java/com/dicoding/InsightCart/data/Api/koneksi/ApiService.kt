package com.dicoding.InsightCart.data.Api.koneksi

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

}