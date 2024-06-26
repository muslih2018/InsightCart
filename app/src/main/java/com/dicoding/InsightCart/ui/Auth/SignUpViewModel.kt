package com.dicoding.InsightCart.ui.Auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.InsightCart.data.Api.koneksi.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.Api.Response.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    companion object{

        private const val TAG = "SignUpViewModel"
    }

    private val _signUpResponse = MutableLiveData<SignUpResponse>()
    val signUpResponse: LiveData<SignUpResponse> = _signUpResponse
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postSignUp(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getLoginApiService().postSignUp(name, email, password)
        client.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _signUpResponse.value = response.body()
                } else {
                    _signUpResponse.value = response.body()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}