package com.dicoding.InsightCart.data.Api.koneksi

import com.dicoding.picodiploma.loginwithanimation.data.Api.Response.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

}