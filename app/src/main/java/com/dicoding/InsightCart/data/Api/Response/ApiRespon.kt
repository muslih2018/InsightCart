package com.dicoding.InsightCart.data.Api.Response

data class ApiResponse(
    val code: Int,
    val description: String
)

enum class ApiResponseCode(val code: Int) {
    SUCCESS(200),
    BAD_REQUEST(400),
    INTERNAL_SERVER_ERROR(500)
}