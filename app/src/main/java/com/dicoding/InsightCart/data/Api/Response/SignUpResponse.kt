package com.dicoding.picodiploma.loginwithanimation.data.Api.Response


import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
)