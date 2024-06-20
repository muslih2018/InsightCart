package com.dicoding.InsightCart.data.Api.koneksi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        // URL untuk operasi login
        private const val BASE_URL_LOGIN = "https://auth-firebase-lfayby47na-et.a.run.app/"

        // URL untuk operasi menampilkan konten
        private const val BASE_URL_CONTENT = "https://backendcapstone-dylk2h7yda-uc.a.run.app/"

        // Fungsi untuk membuat OkHttpClient dengan interceptor sesuai token
        private fun getInterceptor(token: String?): OkHttpClient {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            return if (token.isNullOrEmpty()) {
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token))
                    .addInterceptor(loggingInterceptor)
                    .build()
            }
        }

        // Fungsi untuk mendapatkan ApiService untuk operasi login
        fun getLoginApiService(token: String? = ""): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_LOGIN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor(token))
                .build()
            return retrofit.create(ApiService::class.java)
        }

        // Fungsi untuk mendapatkan ApiService untuk operasi menampilkan konten
        fun getContentApiService(token: String? = ""): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_CONTENT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor(token))
                .build()
            return retrofit.create(ApiService::class.java)
        }

    }
}
